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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.invoker.ElectronBehavior;
import com.lamp.electron.base.common.metadate.ClassificationEnum;
import com.lamp.electron.rpc.api.AbstractElectronBehavior;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * http协议真麻烦
 * 
 * @author laohu
 *
 */
public abstract class HttpBehavior extends AbstractElectronBehavior implements ElectronBehavior {

	private FullHttpMessage httpMessage;

	private HttpHeaders httpHeaders;

	private Charset charset;

	private String contentType = null;

	private boolean isBodyAnalysis = false;

	private Object connectObject;
	
	public HttpBehavior(FullHttpMessage httpMessage) {
		this.httpMessage = httpMessage;
		this.httpHeaders = httpMessage.headers();
		this.content = httpMessage.content();
		String contentType = httpHeaders.get(HttpHeaderNames.CONTENT_TYPE);
		if (Objects.nonNull(contentType)) {
			int charsetIndex = contentType.indexOf("charset=");
			this.contentType = charsetIndex == -1 ? contentType : contentType.substring(0, contentType.indexOf(';'));
			charset = Charset.forName(charsetIndex == -1 ? "UTF-8" : contentType.substring(charsetIndex + 8));
		}
	}

	public HttpBehavior(HttpHeaders httpHeaders, ByteBuf content,
			Throwable throwable) {
		super(throwable);
		this.httpHeaders = httpHeaders;
		this.content = content;

	}

	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}
	
	@Override
	public ProtocolEnum getProtocolEnum() {
		return ProtocolEnum.HTTP;
	}

	/**
	 * 目前值支持JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String data(DataSpot dataSpot, String key) {
		if (DataSpot.HEADER == dataSpot) {
			return httpHeaders.get(key);
		} else if (DataSpot.QUERY == dataSpot) {
			
		} else if (DataSpot.BODY == dataSpot) {
			bodyAnalysis(ClassificationEnum.OBJECT);
			if (HttpHeaderValues.APPLICATION_JSON.toString().equals(this.contentType)) {
				return ((JSONObject) connectObject).getString(key);
			} else {
				List<String> valueList = ((Map<String, List<String>>) connectObject).get(key);
				return valueList == null ? null : valueList.get(0);
			}

		}

		return null;
	}

	@Override
	public Map<String, String> data(DataSpot dataSpot) {

		if (DataSpot.HEADER == dataSpot) {
		} else if (DataSpot.QUERY == dataSpot) {

		} else if (DataSpot.BODY == dataSpot) {

		}

		return null;
	}

	@Override
	public Object data(DataSpot dataSpot, ClassificationEnum classificationEnum) {
		bodyAnalysis(classificationEnum);
		return connectObject;
	}

	@Override
	public void setData(DataSpot dataSpot, String key, String value) {
		
		if(setUserInfo(dataSpot, key, value)) {
			return;
		}
		if (DataSpot.HEADER == dataSpot) {

		} else if (DataSpot.BODY == dataSpot) {

		}

	}

	@Override
	public boolean isResource() {
		return false;
	}


	@Override
	public Object original() {
		return this.httpMessage;
	}

	private void bodyAnalysis(ClassificationEnum classificationEnum) {
		if (!this.isBodyAnalysis) {
			String contentBody = this.content.toString(charset);
			if (HttpHeaderValues.APPLICATION_JSON.toString().equals(this.contentType)) {
				connectObject = ClassificationEnum.LIST == classificationEnum ? JSON.parseArray(contentBody)
						: JSON.parseObject(contentBody);
			} else {
				QueryStringDecoder paramDecoder = new QueryStringDecoder(this.content.toString(charset), false);
				connectObject = paramDecoder.parameters();
			}
			this.isBodyAnalysis = true;
		}

	}
}
