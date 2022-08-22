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
package com.lamp.electron.base.common.invoker;

import com.lamp.electron.base.common.register.data.NetworkAddress;

import io.netty.handler.codec.http.HttpResponseStatus;

public interface ElectronRequest extends ElectronBehavior{

	public String path();
	
	public void path(String path);
	
	public String requestName();	
	
	public AgreementResponse getAgreementResponse();
	
	public NetworkAddress networkAddress();
	
	/**
	 * 
	 * @param httpResponseStatus 不重复造轮子
	 * @return
	 */
	public ElectronResponse electronResponse(HttpResponseStatus httpResponseStatus ,Object headers, Object connect,Throwable throwable);
}
