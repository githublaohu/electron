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

import java.util.Objects;

import org.asynchttpclient.RequestBuilder;

import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.ability.config.http.HttpRequestConfig;
import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.rpc.api.RpcHandle;
import com.lamp.electron.rpc.http.api.HttpBehavior;
import com.lamp.electron.rpc.http.async.AsyncHttpClient.Future;

public class HttpAsyncClient implements Invoker {

	private AsyncHttpClient asyncHttpClient;

	private RpcHandle rpcHandle;

	private Perception<RpcRequestConfig> rpcRequestConfig;


	private String url;


	public HttpAsyncClient(AsyncHttpClient asyncHttpClient, NetworkAddress networkAddress, RpcHandle rpcHandle,
			Perception<RpcRequestConfig> rpcRequestConfig) {
		this.asyncHttpClient = asyncHttpClient;
		this.rpcHandle = rpcHandle;
		this.rpcRequestConfig = rpcRequestConfig;

		url = "http://"+networkAddress.networkAddress() + ":" + networkAddress.port();
	}

	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		HttpRequestConfig httpRequestConfig = (HttpRequestConfig) rpcRequestConfig.pereptionObject();

		RequestBuilder requestBuilder = new RequestBuilder(electronRequest.requestName());
		// url 如果不待参数可以优化。
		requestBuilder.setUrl(url + electronRequest.path());
		// heaher
		if(electronRequest instanceof HttpBehavior) {
			requestBuilder.setHeaders(((HttpBehavior)electronRequest).getHttpHeaders());
		}else {
			requestBuilder.setSingleHeaders(electronRequest.data(DataSpot.HEADER));
		}
		// body
		requestBuilder.setBody(electronRequest.content().nioBuffer());

		if (Objects.nonNull(httpRequestConfig) && Objects.nonNull(httpRequestConfig.getRequestTimeout())) {
			requestBuilder.setRequestTimeout(httpRequestConfig.getRequestTimeout());
		}
		Future future = new Future(electronRequest, electronResponse, invoker);
		asyncHttpClient.executeRequest(requestBuilder.build(), future, this.rpcHandle);
		return ElectronResponse.ANSY_RESPONSE;
	}

}
