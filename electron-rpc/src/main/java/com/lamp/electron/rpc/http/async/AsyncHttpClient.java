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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;

import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.rpc.api.RpcConfig;
import com.lamp.electron.rpc.api.client.RpcClientCreate;
import com.lamp.electron.rpc.http.api.HttpResponse;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncHttpClient {

	private DefaultAsyncHttpClient asyncHttpClient;


	public AsyncHttpClient(RpcClientCreate rpcConfig) {

		DefaultAsyncHttpClientConfig.Builder builder = new DefaultAsyncHttpClientConfig.Builder()
				.setFollowRedirect(true).setIoThreadsCount(RpcConfig.CPU_NUM)
				.setConnectTimeout(30000).setReadTimeout(2000000).setRequestTimeout(30000).setMaxRequestRetry(2)
				.setAllocator(PooledByteBufAllocator.DEFAULT).setCompressionEnforced(true)
				.setThreadPoolName("asyncHttpClient-pool").setMaxConnections(30000).setMaxConnectionsPerHost(30000)
				.setPooledConnectionIdleTimeout(60 * 1000 * 10);
		asyncHttpClient = new DefaultAsyncHttpClient(builder.build());
	}

	public void executeRequest(Request request, Future listener,Executor exec) {
		ListenableFuture<Response> future = asyncHttpClient.executeRequest(request);
		listener.future = future;
		future.addListener(listener, exec);
	}

	protected static  class Future implements Runnable , Invoker {

		private ListenableFuture<Response> future;

		private ElectronRequest electronRequest;
		
		private ElectronResponse electronResponse;
		
		private Invoker invoker;
		
		
		
		public Future( ElectronRequest electronRequest,
				ElectronResponse electronResponse, Invoker invoker) {
			super();
			this.electronRequest = electronRequest;
			this.electronResponse = electronResponse;
			this.invoker = invoker;
		}

		Response response() throws InterruptedException, ExecutionException {
			return future.get();
		}
		
		public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse,Invoker invoker) {
			return this.electronResponse;
		}
		
		public void run() {
			DefaultFullHttpResponse defaultFullHttpResponse;
			Throwable throwable = null;
			try {
				Response response = response();
				defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
						HttpResponseStatus.valueOf(response.getStatusCode()),
						Unpooled.copiedBuffer(response.getResponseBodyAsBytes()), response.getHeaders(),
						response.getHeaders());
				
			} catch (InterruptedException | ExecutionException e) {
				throwable = e;
				defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
						HttpResponseStatus.NOT_FOUND);
				log.error(e.getMessage() , e);
			}
			invoker.run(electronRequest, new HttpResponse(defaultFullHttpResponse, throwable), null);
		}

	}
}
