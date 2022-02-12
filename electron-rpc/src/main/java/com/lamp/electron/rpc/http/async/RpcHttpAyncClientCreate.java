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
package com.lamp.electron.rpc.http.async;

import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.rpc.api.RpcHandle;
import com.lamp.electron.rpc.api.client.RpcClientCreate;

public class RpcHttpAyncClientCreate implements RpcClientCreate {

	private AsyncHttpClient asyncHttpClient;

	public RpcHttpAyncClientCreate(RpcClientCreate rpcConfig) {
		asyncHttpClient = new AsyncHttpClient(rpcConfig);
	}

	@Override
	public Invoker createClient(RpcHandle rpcHandle, NetworkAddress networkAddress, Perception<RpcRequestConfig> rpcRequestConfig) {
		return new HttpAsyncClient(asyncHttpClient, networkAddress, rpcHandle,rpcRequestConfig);
	}
}
