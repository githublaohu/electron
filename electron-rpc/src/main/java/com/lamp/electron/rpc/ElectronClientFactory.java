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
package com.lamp.electron.rpc;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.basedata.NodeBase;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.enums.ProtocolConfigEnum;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.ConfigPerceptionFactory;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.rpc.api.RpcConfig;
import com.lamp.electron.rpc.api.RpcHandle;
import com.lamp.electron.rpc.api.client.RpcClientCreate;
import com.lamp.electron.rpc.dubbo.DubboClientCreate;
import com.lamp.electron.rpc.http.async.RpcHttpAyncClientCreate;
import com.lamp.electron.rpc.message.MessageMiddlewareClientCreate;
import com.lamp.electron.rpc.resources.ResourceClientCreate;

import lombok.Setter;

@Setter
public class ElectronClientFactory {
	private static final Map<ProtocolEnum, ProtocolConfigEnum> PROTECONLE_REQUSET_CONFIG = new HashMap<>();

	static {
		for (ProtocolConfigEnum protocolConfigEnum : ProtocolConfigEnum.values()) {
			PROTECONLE_REQUSET_CONFIG.put(protocolConfigEnum.getProtocolEnum(), protocolConfigEnum);
		}
	}

	private Map<ProtocolEnum, RpcClientCreate> rpcClientCreateMap = new HashMap<>();

	@Resource
	private ConfigPerceptionFactory perceptionFactory;

	private RpcHandle rpcHandle;
	
	{
		rpcClientCreateMap.put(ProtocolEnum.HTTP,
				new RpcHttpAyncClientCreate(rpcClientCreateMap.get(ProtocolEnum.HTTP)));
		rpcClientCreateMap.put(ProtocolEnum.DUBBO, new DubboClientCreate());
		rpcClientCreateMap.put(ProtocolEnum.RESOURCE, new ResourceClientCreate());
		rpcClientCreateMap.put(ProtocolEnum.REDIS, null);
		rpcClientCreateMap.put(ProtocolEnum.MESSAGE_MIDDLEWARE, new MessageMiddlewareClientCreate());
	}




	public void createFactory(NetworkAddress networkAddress, RpcConfig rpcConfig) {
		rpcClientCreateMap.get(networkAddress.protocol()).createFactory(networkAddress, rpcConfig);
	}

	public Invoker crateRpcClient(NetworkAddress networkAddress) {
		return crateRpcClient(networkAddress, rpcHandle, null);
	}

	public Invoker crateRpcClient(NetworkAddress networkAddress, RpcHandle rpcHandle,
			Perception<RpcRequestConfig> rpcConfig) {
		if (Objects.isNull(rpcConfig)) {
			NodeBase nodeBase = (NodeBase) networkAddress;
			Class<?> clazz = PROTECONLE_REQUSET_CONFIG.get(nodeBase.getProtocol()).getClazz();
			if ( Objects.equals(nodeBase.getApplicationEnglishName(), nodeBase.name())) {
				if(nodeBase.getProtocol() == ProtocolEnum.HTTP) {
				rpcConfig = perceptionFactory.getPerception(clazz, networkAddress.name(),
						OrganizationTypeEnum.APPLICATION, null, OrganizationTypeEnum.SYSTEM_DEFAULT.name(),null);
				}else {
					return null;
				}
			} else {
				rpcConfig = perceptionFactory.getPerception(clazz, networkAddress.name(),
						OrganizationTypeEnum.INTERFACE, OrganizationTypeEnum.APPLICATION,
						nodeBase.getApplicationEnglishName(), null);
			}
		}

		return rpcClientCreateMap.get(networkAddress.protocol())
				.createClient(Objects.isNull(rpcHandle) ? this.rpcHandle : rpcHandle, networkAddress, rpcConfig);
	}
}
