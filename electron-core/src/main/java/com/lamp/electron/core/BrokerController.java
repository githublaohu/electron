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
package com.lamp.electron.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.electron.base.common.register.ElectronPrefixFactory;
import com.lamp.electron.base.common.register.data.ContainerInfo;
import com.lamp.electron.base.common.register.server.CodeExampleRegister;
import com.lamp.electron.base.common.register.server.ContainerRegister;
import com.lamp.electron.core.container.ContainerConfig;
import com.lamp.electron.core.container.ElectronContainer;
import com.lamp.electron.register.api.DefaultRegisterFactory;
import com.lamp.electron.register.api.RegisterFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author laohu
 *
 */
@Slf4j
public class BrokerController {

	private Map<String, ElectronContainer> electronContainerMap = new ConcurrentHashMap<>();

	private RegisterFactory registerFactory;

	private ElectronConfig electronConfig;
	
	private CodeExampleRegister codeExampleRegister;
	
	private ContainerRegister containerRegister;

	public BrokerController(ElectronConfig electronConfig,List<ElectronConfig> electronConfigList) {
		this.electronConfig = electronConfig;
		this.registerFactory =  new DefaultRegisterFactory(electronConfig.getRegister() , ElectronPrefixFactory.PREIFX_FACTORY,"container");
	}

	
	public void start() throws Exception {
		// 初始化基础的信息
		// 初始化容器
		
		// 初始化容器监听
		ContainerInfoRegister containersRegister = new ContainerInfoRegister();
		registerFactory.createMonitorObject(containersRegister);
		// 实例注册
		codeExampleRegister = registerFactory.createRegisterObject(CodeExampleRegister.class);
		// 容器注册
		containerRegister   = registerFactory.createRegisterObject(ContainerRegister.class);
		// 初始化报警功能
		
		
		this.createElectronContainer(this.electronConfig.getDefaults());
	}

	public synchronized void createElectronContainer(ContainerConfig containerConfig) {
		String containerName = containerConfig.getContainerName();
		ElectronContainer electronContainer = electronContainerMap.get(containerName);
		try {
			if (Objects.isNull(electronContainer)) {
				if(Objects.isNull(containerConfig.getRegister())) {
					containerConfig.setRegister(electronConfig.getRegister());
				}
				electronContainer = new ElectronContainer(containerConfig);
				electronContainerMap.put("", electronContainer);
			} else {

			}
		} catch (Exception e) {
			log.error("容器创建失败，容易信息是{}", containerConfig, e);
		}
	}

	public void shutdown() {

	}

	class ContainerInfoRegister implements ContainerRegister {

		@Override
		public int register(ContainerInfo t) {
			BrokerController.this.createElectronContainer(null);
			return 0;
		}

		@Override
		public int unRegister(ContainerInfo t) {

			return 0;
		}

	}

}
