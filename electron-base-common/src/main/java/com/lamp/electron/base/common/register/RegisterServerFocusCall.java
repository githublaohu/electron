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
package com.lamp.electron.base.common.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.electron.base.common.register.server.AbilityRelationRegister;
import com.lamp.electron.base.common.register.server.BehaviorRegister;
import com.lamp.electron.base.common.register.server.CodeExampleRegister;
import com.lamp.electron.base.common.register.server.ContainerRegister;
import com.lamp.electron.base.common.register.server.ExampleRegister;
import com.lamp.electron.base.common.register.server.InterfaceRegister;
import com.lamp.electron.base.common.register.server.OrganizationRegister;
import com.lamp.electron.register.api.DefaultRegisterFactory;
import com.lamp.electron.register.api.RegisterFactory;
import com.lamp.electron.register.api.RegisterServer;

/**
 * 每个容器一个</br>
 * client是否可以两个？</br>
 * 主要是解决多注册中心的问题
 * @author laohu
 *
 */
public class RegisterServerFocusCall {

	private RegisterFactory registerFactory;
	
	private Map<Class<?>,RegisterServer<?>> registerServerMap = new ConcurrentHashMap<>();

	public RegisterServerFocusCall(String register, String prefix) {

		String[] prefixArray = prefix.split(";");
		if (prefixArray.length == 1) {
			registerFactory = new DefaultRegisterFactory(register, ElectronPrefixFactory.PREIFX_FACTORY, prefix);
		} else {
			// 主要是客户端支持向对个容器注册
			RegisterFactoryProxy registerFactory = new RegisterFactoryProxy();
			for (String newPrefix : prefixArray) {
				registerFactory.prefixRegisterFactory
						.add(new DefaultRegisterFactory(register, ElectronPrefixFactory.PREIFX_FACTORY, newPrefix));
			}
			this.registerFactory = registerFactory;
		}
	}
	
	public void regisetServer(List<RegisterServer<?>> registerServerList ) throws Exception {
		registerFactory.createMonitorObjectTo(registerServerList);
	}
	
	public AbilityRelationRegister createAbilityRelationRegister() {
		return createRegister(AbilityRelationRegister.class);
	}
	
	public BehaviorRegister createBehaviorRegister() {
		return createRegister(BehaviorRegister.class);
	}
	
	public CodeExampleRegister createCodeExampleRegister() {
		return createRegister(CodeExampleRegister.class);
	}
	
	public ContainerRegister createContainerRegister() {
		return createRegister(ContainerRegister.class);
	}
	
	public ExampleRegister createExampleRegister() {
		return createRegister(ExampleRegister.class);
	}

	public InterfaceRegister createInterfaceRegister() {
		return createRegister(InterfaceRegister.class);
	}
	
	@SuppressWarnings({  "unchecked" })
	private <T>T createRegister(Class<?> clazz){
		Object register = registerServerMap.get(clazz);
		if(Objects.isNull(register)) {
			register = registerServerMap.computeIfAbsent(clazz, k -> registerFactory.createRegisterObject(clazz));
		}
		return (T)register;
	}
	
	public OrganizationRegister createOrganizationRegister() {
		return registerFactory.createRegisterObject(OrganizationRegister.class);
	}

	class RegisterFactoryProxy implements RegisterFactory {

		private List<RegisterFactory> prefixRegisterFactory = new ArrayList<>();

		@SuppressWarnings("unchecked")
		@Override
		public <T> T createRegisterObject(Class<?> clazz) {
			RegisterServerProxy registerServerProxy = new RegisterServerProxy();
			prefixRegisterFactory
					.forEach(k -> registerServerProxy.registerServerList.add(k.createRegisterObject(clazz)));
			return (T) registerServerProxy;
		}

		@Override
		public void createMonitorObjectTo(List<RegisterServer<?>> registerServerList) throws Exception {

			for (RegisterFactory registerFactory : prefixRegisterFactory) {
				registerFactory.createMonitorObjectTo(registerServerList);
			}
		}

		@Override
		public void createMonitorObject(RegisterServer<?> registerServerList) throws Exception {
			for (RegisterFactory registerFactory : prefixRegisterFactory) {
				registerFactory.createMonitorObject(registerServerList);
			}

		}

		@Override
		public void createMonitorObject(List<RegisterServer<Object>> registerServerList, String url, String prefix)
				throws Exception {

		}

	}

	class RegisterServerProxy implements RegisterServer<Object> {

		List<RegisterServer<Object>> registerServerList = new ArrayList<>();

		@Override
		public int register(Object t) {
			registerServerList.forEach(k -> k.register(t));
			return 0;
		}

		@Override
		public int unRegister(Object t) {
			registerServerList.forEach(k -> k.unRegister(t));
			return 0;
		}

	}

}
