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
package com.lamp.electron.core.container;

import com.lamp.electron.base.common.basedata.EnvironmentalBase;
import com.lamp.electron.rpc.api.RpcConfig;
import com.lamp.electron.rpc.http.config.HttpRpcClientConfig;
import com.lamp.electron.rpc.http.config.HttpRpcServerConfig;

import lombok.Data;

@Data
public class ContainerConfig {

	
	private String register;
	
	private String rocketMQNameServcie;
	
	private String containerName = EnvironmentalBase.DEFAULT_ENVIROMENTAL_NAME;
	
	private HttpRpcServerConfig httpRpcServerConfig = new HttpRpcServerConfig();
	
	private HttpRpcClientConfig httpRpcClientConfig = new HttpRpcClientConfig(); 
	
	private Integer handleThreadNum = RpcConfig.CPU_NUM;

	

}
