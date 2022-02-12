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
package com.lamp.electron.client.dubbo;

import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import com.lamp.electron.base.common.constant.ElectronConstant;
import com.lamp.electron.client.AbstractTransmit;
import com.lamp.electron.client.ElectronProperties;

@Activate(group = PROVIDER, order = -10000)
public class ElectronFilter extends AbstractTransmit implements Filter {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void setElectronProperties(ElectronProperties electronProperties) throws ClassNotFoundException {
		super.init(electronProperties);
	}

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		try {
			super.transmit(RpcContext.getContext().getAttachment(ElectronConstant.USER_INFO_KEY_NAME));
		} catch (Exception e) {
			logger.error(e);
		}
		return invoker.invoke(invocation);
	}

}
