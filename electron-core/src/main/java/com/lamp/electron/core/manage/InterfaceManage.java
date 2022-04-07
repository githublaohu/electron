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
package com.lamp.electron.core.manage;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.lamp.electron.base.common.register.data.InterfaceInfo;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.base.common.register.server.InterfaceRegister;
import com.lamp.electron.base.common.service.ServiceSign;
import com.lamp.electron.rpc.ElectronClientFactory;

public class InterfaceManage implements InterfaceRegister {

	private final Map<String/**/ , LongRangeWrapper> pathToInterface = new ConcurrentHashMap<>();

	private final Map<String/* 项目名 */, Map<Class<?>, Map<Method, LongRangeWrapper>>> serviceInterface = new ConcurrentHashMap<>();

	private final Map<String, AtomicLong> applicationNameAtiomic = new ConcurrentHashMap<>();

	private ElectronClientFactory electronClientFactory;

	public InterfaceManage(ElectronClientFactory electronClientFactory) {
		this.electronClientFactory = electronClientFactory;
	}

	@Override
	public int register(InterfaceInfo interfaceInfo) {
		LongRangeWrapper longRangeWrapper = getLongRangeWrapper(interfaceInfo);
		if (Objects.isNull(longRangeWrapper)) {
			String path = interfaceInfo.getPath();
			longRangeWrapper = pathToInterface.get(path);
			if (Objects.isNull(longRangeWrapper)) {
				String applicationName = interfaceInfo.getApplicationEnglishName();
				longRangeWrapper = pathToInterface.computeIfAbsent(path,
						k -> new LongRangeWrapper(path, applicationName,
								applicationNameAtiomic.computeIfAbsent(applicationName, kk -> new AtomicLong())));
			}
		}else {
			if(Objects.isNull(longRangeWrapper.getPath())) {
				longRangeWrapper.setPath(interfaceInfo.getPath());
			}
		}
		// 这里面需要什么？ 配置，rpc工程，配置哪里来？
		// 需要资源管理器
		interfaceInfo.setInvoker(electronClientFactory.createRpcClient(interfaceInfo));
		longRangeWrapper.addNetworkAddress(interfaceInfo);
		return 0;
	}

	@Override
	public int deregister(InterfaceInfo interfaceInfo) {
		String path = interfaceInfo.getPath();
		LongRangeWrapper longRangeWrapper = pathToInterface.get(path);
		longRangeWrapper.removeNetworkAddress(interfaceInfo);
		return 0;
	}

	public synchronized void register(LongRangeWrapper agent) {
		LongRangeWrapper longRangeWrapper = pathToInterface.get(agent.getPath());
		if (Objects.isNull(longRangeWrapper.getAgent())) {
			agent.setAgent(longRangeWrapper);
		} else {
			agent.setAgent(longRangeWrapper.getAgent());
			longRangeWrapper.setAgent(null);
		}
		pathToInterface.put(longRangeWrapper.getPath(), agent);
	}

	public synchronized int unRegister(String agent) {
		LongRangeWrapper longRangeWrapper = pathToInterface.get(agent);
		if (Objects.nonNull(longRangeWrapper)) {
			if (Objects.nonNull(longRangeWrapper.getAgent())) {
				pathToInterface.put(agent, longRangeWrapper.getAgent());
			} else {
				pathToInterface.remove(agent);
			}
		}
		return 0;
	}

	public LongRangeWrapper getLongRangeWrapper(String path) {
		return pathToInterface.get(path);
	}

	public Map<Method, LongRangeWrapper> getLongRangeWrapper(String name, Class<?> clazz) {
		Map<Class<?>, Map<Method, LongRangeWrapper>> clazzMap = serviceInterface.get(name);
		if (Objects.isNull(clazzMap)) {
			clazzMap = serviceInterface.computeIfAbsent(name, k -> new ConcurrentHashMap<>());
		}

		Map<Method, LongRangeWrapper> map = clazzMap.get(clazz);
		if (Objects.isNull(map)) {
			map = clazzMap.computeIfAbsent(clazz, k -> {
				Map<String, Method> methodMap = ServiceSign.getServiceByMethod(clazz);

				Map<Method, LongRangeWrapper> longRangeMap = new HashMap<>(methodMap.size());
				for (Map.Entry<String, Method> entry : methodMap.entrySet()) {
					LongRangeWrapper longRangeWrapper = new LongRangeWrapper(null, name,
							applicationNameAtiomic.computeIfAbsent(name, kk -> new AtomicLong()));
					longRangeMap.put(entry.getValue(), longRangeWrapper);
				}

				return longRangeMap;
			});
		}
		return map;
	}

	private LongRangeWrapper getLongRangeWrapper(InterfaceInfo interfaceInfo) {
		Integer servierSign = interfaceInfo.getServierSign();
		if (Objects.isNull(servierSign) || servierSign == 0) {
			return null;
		}
		Class<?> clazz = ServiceSign.getService(servierSign);
		Map<Method, LongRangeWrapper> servierWrapper = getLongRangeWrapper(interfaceInfo.getApplicationEnglishName(),
				clazz);

		return servierWrapper.get(ServiceSign.getServiceByMethod(clazz, interfaceInfo.getMethodName()));
	}
}
