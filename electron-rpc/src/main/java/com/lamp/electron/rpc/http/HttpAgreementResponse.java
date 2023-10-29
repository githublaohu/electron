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
package com.lamp.electron.rpc.http;

import java.util.Objects;

import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.rpc.api.AbstractAgreementResponse;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.util.ReferenceCountUtil;

public class HttpAgreementResponse extends AbstractAgreementResponse<ChannelHandlerContext>{

	private FullHttpRequest fullHttpRequest;
	
	
	public void setFullHttpRequest(FullHttpRequest fullHttpRequest) {
		this.fullHttpRequest = fullHttpRequest;
	}
	
	@Override
	public void reply(ElectronResponse electronResponse, ElectronRequest electronRequest) {
		ReferenceCountUtil.release(fullHttpRequest);
		ChannelFuture	channelFuture= t.writeAndFlush(electronResponse.original());
		if(Objects.isNull(electronRequest.data(DataSpot.HEADER, HttpHeaderValues.KEEP_ALIVE.toString()))) {
			channelFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}

}
