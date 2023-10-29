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
package com.lamp.electron.console.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lamp.electron.base.common.basedata.EnvironmentalBase;
import com.lamp.electron.base.common.register.RegisterServerFocusCall;
import com.lamp.electron.base.common.register.data.ContainerInfo;
import com.lamp.electron.register.api.RegisterServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RegisterObject {

	@Value("${electron.registerAddress}")
	private String registerAddress;

	
	
	@Autowired
	private List<RegisterServer<?>> registerServerList = new ArrayList<>();

	private Map<String, RegisterServerFocusCall> prefixRegisterServerFocusCallMap = new HashMap<>();

	@PostConstruct
	private void init() {
		try {
			// 创建默认容器
			RegisterServerFocusCall registerServerFocusCall = new RegisterServerFocusCall(registerAddress,
					EnvironmentalBase.DEFAULT_ENVIROMENTAL_NAME);
			this.registerServer(registerServerFocusCall);
			prefixRegisterServerFocusCallMap.put(EnvironmentalBase.DEFAULT_ENVIROMENTAL_NAME, registerServerFocusCall);
			// 读取所有容器
			
			
			// 创建所有中心
		} catch (Exception e) {
			log.error("Register Server error,  {}", e);
		}

	}

	private void registerServer(RegisterServerFocusCall registerServerFocusCall) {
		try {
			registerServerFocusCall.registerServer(registerServerList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public RegisterServerFocusCall getRegisterServerFocusCall(EnvironmentalBase environmentalBase) {
		String environmentalName = environmentalBase.getEnvironmentalName();
		if(Objects.isNull(environmentalName)) {
			environmentalName = EnvironmentalBase.DEFAULT_ENVIROMENTAL_NAME;
		}
		RegisterServerFocusCall registerServerFocusCall = prefixRegisterServerFocusCallMap.get(environmentalName);
		return registerServerFocusCall;
	}

	public synchronized void createEnvironmentRegisterServer(ContainerInfo containerInfo) {
		
	}

	public void removeEnvironmentRegisterServer(ContainerInfo containerInfo) {

	}
}
