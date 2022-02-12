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
package com.lamp.electron.rpc.dubbo;

import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.rpc.api.AbstractRpcBase;
import com.lamp.electron.rpc.api.RpcHandle;
import com.lamp.electron.rpc.api.RpcServer;

public class DubboServer extends AbstractRpcBase implements RpcServer{

	public DubboServer( RpcHandle rpcHandle) {
		super(rpcHandle,null);
		
	}

	@Override
	public ProtocolEnum rpcType() {
		return ProtocolEnum.DUBBO;
	}

	@Override
	public void reply(ElectronResponse electronResponse) {
		
		
	}

	@Override
	protected void init() {
		
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
