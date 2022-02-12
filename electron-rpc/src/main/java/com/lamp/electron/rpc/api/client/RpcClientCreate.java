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
package com.lamp.electron.rpc.api.client;

import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.rpc.api.RpcConfig;
import com.lamp.electron.rpc.api.RpcHandle;

public interface RpcClientCreate {

	/**
	 * 主动协议如何处理，目前http-client是默认，dubbo使用dubbo自己的体系<br/>
	 * 如果传递RpcConfig配置<br/>
	 * 目前不管http,dubbo的动态化创建client，现在的问题是 第二点<br/>
	 * 
	 * @param rpcHandle
	 * @param networkAddress
	 * @param rpcRequestConfig 必须 perception话
	 * @return
	 */
	public Invoker createClient(RpcHandle rpcHandle, NetworkAddress networkAddress, Perception<RpcRequestConfig> rpcRequestConfig);

	/**
	 * 比如http，dubbo，grpc这种主动注册的可以在，createClient里面处理。<br/>
	 * rpcConfig要到那个级别，是app？
	 * 
	 * @param networkAddress
	 * @param object
	 */
	public default void createFactory(NetworkAddress networkAddress, RpcConfig object) {
	}
}
