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

import java.util.Objects;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.rpc.api.RpcConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 没办法，只能桥接下
 * 
 * @author laohu
 *
 */
@Slf4j
public class DefaultMQProducerFactory {

	private volatile NetworkAddress networkAddress;
	
	@SuppressWarnings("unused")
	private volatile RpcConfig rpcConfig;

	private volatile DefaultMQProducer producer;

	public DefaultMQProducerFactory(NetworkAddress networkAddress,RpcConfig rpcConfig) {
		this.networkAddress = networkAddress;
		this.rpcConfig = rpcConfig;
	}

	public void reload(NetworkAddress networkAddress,RpcConfig rpcConfig) {
		this.networkAddress = networkAddress;
		this.rpcConfig = rpcConfig;
		this.producer = null;
	}

	public DefaultMQProducer getDefaultMQProducer() {
		DefaultMQProducer producer = this.producer;
		if (Objects.nonNull(producer)) {
			return producer;
		}
		synchronized (this) {
			producer = this.producer;
			if (Objects.nonNull(producer)) {
				return producer;
			}
			producer = new DefaultMQProducer("electron_" + networkAddress.name() + "_producer");
			producer.setNamesrvAddr(networkAddress.networkAddress());
			try {
				producer.start();
				this.producer = producer;
				return this.producer;
			} catch (MQClientException e) {
				log.error(e.getMessage(), e);
				return this.producer;
			}
		}
	}

	public String toString() {
		return networkAddress.toString();
	}
}
