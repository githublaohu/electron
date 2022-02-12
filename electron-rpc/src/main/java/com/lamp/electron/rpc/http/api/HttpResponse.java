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
package com.lamp.electron.rpc.http.api;

import java.util.Objects;

import com.lamp.electron.base.common.invoker.ElectronResponse;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HttpResponse extends HttpBehavior implements ElectronResponse{

	private HttpResponseStatus responseStatus;

	private DefaultFullHttpResponse defaultFullHttpResponse;

	public HttpResponse(HttpResponseStatus status, ByteBuf content, HttpHeaders headers, Throwable throwable) {
		super(headers, content, throwable);
		this.responseStatus = status;
	}

	public HttpResponse(DefaultFullHttpResponse defaultFullHttpResponse, Throwable throwable) {
		super(defaultFullHttpResponse.headers(), defaultFullHttpResponse.content(), throwable);
		this.defaultFullHttpResponse = defaultFullHttpResponse;
	}

	@Override
	public int statusCode() {
		return responseStatus.code();
	}

	@Override
	public Object original() {
		if (Objects.nonNull(defaultFullHttpResponse) && !this.isPerceptionContent) {
			return defaultFullHttpResponse;
		}
		return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
				this.isPerceptionContent ? HttpResponseStatus.OK : responseStatus, content(), getHttpHeaders(),
				getHttpHeaders());
	}

}
