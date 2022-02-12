/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.electron.core.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.invoker.AgreementResponse;
import com.lamp.electron.base.common.invoker.ElectronBehavior;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.metadate.ClassificationEnum;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.base.common.service.authentication.AuthenticationService;
import com.lamp.electron.core.container.ElectronRpcHandle;
import com.lamp.electron.core.manage.InterfaceManage;
import com.lamp.electron.core.service.asyn.AuthenticationAsynService;
import com.lamp.electron.rpc.api.AbstractElectronBehavior;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Setter;

/**
 * <ol>
 * <li>service 接口管理</li>
 * <li>代理对象</li>
 * <li>调用链路</li>
 * <li>异步管理</li>
 * <li>rpc管理</li>
 * <li>穿透</li>
 * <li></li>
 * </ol>
 * 
 * @author laohu
 *
 */
@Setter
public class InsideServiceFactory {

	private static final Map<Class<?>, Class<?>> CLASS_ASYN = new HashMap<>();

	private static final Map<Class<?>, Map<Method, Method>> CLASS_METHOD_MAP = new HashMap<>();

	static {
		CLASS_ASYN.put(AuthenticationAsynService.class, AuthenticationService.class);

		CLASS_ASYN.forEach((key, value) -> {
			Map<Method, Method> map = new HashMap<>();
			CLASS_METHOD_MAP.put(key, map);
			for (Method method : key.getMethods()) {
				try {
					for (Method mapMethod : value.getMethods()) {
						if (mapMethod.getName().equals(method.getName())) {
							map.put(method, mapMethod);
						}
					}
				} catch (SecurityException e) {
					throw new RuntimeException(e);
				}
			}
			if (map.size() != value.getMethods().length) {
				throw new RuntimeException("内部对应映射失败");
			}
		});
	}

	private final Map<String/* 项目名 */, Map<Class<?>, Object>> serviceInterface = new ConcurrentHashMap<>();

	private InterfaceManage interfaceManage;

	private ElectronRpcHandle electronRpcHandle;

	@SuppressWarnings("unchecked")
	public <T> T getService(String name, Class<?> clazz, Objects objects) {
		Map<Class<?>, Object> serviceMap = serviceInterface.get(name);
		if (Objects.isNull(serviceMap)) {
			serviceMap = serviceInterface.computeIfAbsent(name, k -> new ConcurrentHashMap<>());
		}
		Object object = serviceMap.get(clazz);
		if (Objects.isNull(object)) {
			object = serviceMap.computeIfAbsent(clazz, k -> {
				ServiceProxyHandler serviceProxyHandler = new ServiceProxyHandler();
				serviceProxyHandler.longRangeMap = interfaceManage.getLongRangeWrapper(name, CLASS_ASYN.get(clazz));
				serviceProxyHandler.insideMap = CLASS_METHOD_MAP.get(clazz);
				return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
						new Class[] { clazz }, serviceProxyHandler);
			});
		}
		return (T) object;
	}

	public class ServiceProxyHandler implements InvocationHandler {

		private Map<Method, LongRangeWrapper> longRangeMap;

		private Map<Method, Method> insideMap;

		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if ("toString".equals(method.getName())) {
				return super.toString();
			} else if ("hashCode".equals(method.getName())) {
				return super.hashCode();
			} else if ("equals".equals(method.getName())) {
				return super.equals(args[0]);
			}

			LongRangeWrapper longRangeWapper = longRangeMap.get(insideMap.get(method));
			if (longRangeWapper.getNetworkAddress().size() == 0) {
				ServiceAgreementResponse<Object> agreement = (ServiceAgreementResponse) args[0];
				agreement.reply(null);
			}

			ServiceElectronRequest serviceElectronRequest = new ServiceElectronRequest();
			serviceElectronRequest.path = longRangeWapper.getPath();
			serviceElectronRequest.agreementResponse = (AgreementResponse) args[0];
			serviceElectronRequest.object = args[1];
			electronRpcHandle.receive(serviceElectronRequest, longRangeWapper);
			return null;
		}
	}

	public static abstract class ServiceAgreementResponse<T> implements AgreementResponse {

		protected final Type type;

		protected ServiceAgreementResponse() {
			Type superClass = getClass().getGenericSuperclass();
			type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
		}

		@Override
		@SuppressWarnings("unchecked")
		public void reply(ElectronResponse electronResponse) {
			Throwable throwable = electronResponse.throwable();
			if (Objects.isNull(throwable)) {
				T object = (T) JSON.parseObject(electronResponse.contentString(), type);
				serviceReturn(object, electronResponse);
			} else {
				serviceReturn(null, electronResponse);
			}
		}

		public abstract void serviceReturn(T t, ElectronResponse electronResponse);

		protected void cache(String key, String data, Long cacheTime) {

		}
	}

	public static class ServiceElectronRequest extends AbstractElectronBehavior implements ElectronRequest {

		private AgreementResponse agreementResponse;

		private Object object;

		private String path;

		@Override
		public ProtocolEnum getProtocolEnum() {
			return ProtocolEnum.SERVICE;
		}

		@Override
		public String data(DataSpot dataSpot, String key) {
			return null;
		}

		@Override
		public Map<String, String> data(DataSpot dataSpot) {
			return null;
		}

		@Override
		public Object data(DataSpot dataSpot, ClassificationEnum classificationEnum) {
			return JSON.toJSONString(object);
		}

		@Override
		public void setData(DataSpot dataSpot, String key, String value) {

		}

		@Override
		public ByteBuf content() {
			byte[] bytes = JSON.toJSONBytes(object);
			return Unpooled.directBuffer(bytes.length, bytes.length).writeBytes(bytes);
		}

		@Override
		public Object original() {
			return null;
		}

		@Override
		public void assignment(ElectronBehavior electronBehavior) {

		}

		@Override
		public String path() {
			return path;
		}

		@Override
		public void path(String path) {
		}

		@Override
		public String requestName() {
			return "POST";
		}

		@Override
		public AgreementResponse getAgreementResponse() {
			return agreementResponse;
		}

		@Override
		public NetworkAddress networkAddress() {
			return this.networkAddress;
		}

		@Override
		public ElectronResponse electronResponse(HttpResponseStatus httpResponseStatus, Object headers, Object connet,
				Throwable throwable) {
			return null;
		}

	}

}
