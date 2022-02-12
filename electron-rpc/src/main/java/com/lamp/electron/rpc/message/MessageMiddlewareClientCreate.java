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
package com.lamp.electron.rpc.message;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.rpc.api.RpcConfig;
import com.lamp.electron.rpc.api.RpcHandle;
import com.lamp.electron.rpc.api.client.RpcClientCreate;

public class MessageMiddlewareClientCreate implements RpcClientCreate {

	private Map<String, DefaultMQProducerFactory> networkAddressMap = new ConcurrentHashMap<>();

	public void createFactory(NetworkAddress networkAddress, RpcConfig rpcConfig) {
		DefaultMQProducerFactory defaultMQProducerFactory = networkAddressMap.get(networkAddress.name());
		if(Objects.nonNull(defaultMQProducerFactory)) {
			defaultMQProducerFactory = networkAddressMap.computeIfAbsent(networkAddress.name(),
					key -> new DefaultMQProducerFactory(networkAddress, rpcConfig));
		}
		defaultMQProducerFactory.reload(networkAddress, rpcConfig);
	}

	@Override
	public Invoker createClient(RpcHandle rpcHandle, NetworkAddress networkAddress, Perception<RpcRequestConfig> rpcRequestConfig) {
		return new MessageMiddlewareClient(rpcHandle, networkAddressMap.get(networkAddress.name()), rpcRequestConfig);
	}

}
