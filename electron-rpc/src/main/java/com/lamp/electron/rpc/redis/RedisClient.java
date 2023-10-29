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
package com.lamp.electron.rpc.redis;

import com.lamp.electron.base.common.ability.RedisRPC;
import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.rpc.api.AbstractRpcBase;
import com.lamp.electron.rpc.api.RpcHandle;

/**
 * 等 ledis
 * @author laohu
 *
 */
public class RedisClient extends AbstractRpcBase implements Invoker{

	public RedisClient(Perception<RpcRequestConfig> perception, RpcHandle rpcHandle) {
		super(perception, rpcHandle);
		
	}

	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {

		RedisRPC redisRPC = getRequestConfig();


		return null;
	}

	@Override
	protected void init() {
		
	}

}
