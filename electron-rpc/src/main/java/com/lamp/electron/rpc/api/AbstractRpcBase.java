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
package com.lamp.electron.rpc.api;

import com.lamp.electron.base.common.RemotingUtil;
import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.Perception;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.epoll.Epoll;

/**
 * <p>
 * 1. 异步化
 * </p>
 * <p>
 * 2. 每个接口单独配置
 * </p>
 * <p>
 * 3. client配置
 * </p>
 * <p>
 * 4. 异步化
 * </p>
 * <p>
 * 5. 异步化
 * </p>
 * 
 * @author laohu
 *
 */
public abstract class AbstractRpcBase {

	private RpcHandle rpcHandle;

	private Perception<RpcRequestConfig> perception;
	
	private  RpcConfig rpcConfig;

	public AbstractRpcBase(RpcHandle rpcHandle,RpcConfig rpcConfig) {
		this.rpcHandle = rpcHandle;
		this.rpcConfig = rpcConfig;
		init();
	}

	public AbstractRpcBase(Perception<RpcRequestConfig> perception, RpcHandle rpcHandle) {
		this.rpcHandle = rpcHandle;
		this.perception = perception;
		init();
	}

	@SuppressWarnings("unchecked")
	protected <T extends RpcRequestConfig> T getRequestConfig() {
		return (T) perception.pereptionObject();
	}

	@SuppressWarnings("unchecked")
	protected <T extends RpcConfig> T getRpcConfig() {
		return (T)rpcConfig;
	}
	
	protected abstract void init();

	protected void receive(ElectronRequest electronRequest) {
		rpcHandle.receive(electronRequest);
	}

	protected void callback(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		rpcHandle.callback(electronRequest, electronResponse, invoker);
	}

	protected boolean useEpoll() {
		return RemotingUtil.isLinuxPlatform() && Epoll.isAvailable();
	}

	protected ElectronRequest createRequest(Object object,
			AbstractAgreementResponse<ChannelHandlerContext> httpAgreementResponse) {

		return null;
	}

}
