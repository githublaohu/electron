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

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.invoker.AgreementResponse;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.register.data.NetworkAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HttpRequest extends HttpBehavior implements ElectronRequest {

	private FullHttpRequest fullHttpRequest;

	private int update = 0;

	private Map<String, String> pathData;

	private String uri;
	
	private String rewrieturi;

	private String query;

	private int index;

	private AgreementResponse agreementResponse;

	public HttpRequest(FullHttpRequest fullHttpRequest, AgreementResponse agreementResponse) {
		super(fullHttpRequest);
		this.agreementResponse = agreementResponse;
		this.fullHttpRequest = fullHttpRequest;
		this.uri = fullHttpRequest.uri();
		this.index = uri.indexOf("?");
		if(index > 0) {
			this.uri = uri.substring(0, index);
		}
	}

	public String data(DataSpot dataSpot, String key) {
		if (DataSpot.URL != dataSpot) {
			return super.data(dataSpot, key);
		} else {
			return uri.startsWith(key) ? key : "";
		}
	}

	@Override
	public String path() {
		return uri;
	}

	@Override
	public String requestName() {
		return fullHttpRequest.method().name();
	}

	@Override
	public NetworkAddress networkAddress() {
		return this.networkAddress;
	}

	@Override
	public Object original() {
		if (update == 0) {
			return fullHttpRequest;
		}
		return null;
	}

	@Override
	public AgreementResponse getAgreementResponse() {
		return this.agreementResponse;
	}

	@Override
	public ElectronResponse electronResponse(HttpResponseStatus httpResponseStatus, Object headers, Object connet,Throwable throwable) {
		return new HttpResponse(httpResponseStatus,createConnetByteBuf(connet),(HttpHeaders)headers,throwable);
	}
	
	private ByteBuf createConnetByteBuf( Object connet) {
		if(Objects.isNull(connet)) {
			return Unpooled.EMPTY_BUFFER;
		}
		if(connet instanceof String) {
			ByteBuf buf = Unpooled.buffer();
			buf.writeCharSequence((String)connet, Charset.defaultCharset());
			return buf;
		}
		return (ByteBuf)connet;
		
	}

	@Override
	public void path(String path) {
		this.rewrieturi = path;
	}

}
