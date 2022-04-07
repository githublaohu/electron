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
package com.lamp.electron.base.common.exception;

import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Getter;

/**
 * 容器异常，异常状态码1000开始<br/>
 * RPC异常，异常状态码2000开始<br/>
 * 1. rpc服务 2000
 * 2. rpc-client 2200
 * 3. rpc-处理 2400
 * 动作异常，异常状态码5000开始，每个动作30个异常<br/>
 * 
 * @author laohu
 *
 */
@Getter
public enum ExceptionType {

	REQUEST_RESOURCE_NOT_FIND(2500 ,"404", "The request could not find the corresponding resource"),
	REQUSET_GET_NOT_SERVICE(2501,"out of instance","Failed to get service"),
	REQUSET_NOT_INSTANCE(2502,"no instance","No instance exists, service name is %s"),
	REQUSET_CODE_OUT_TIME(2503 ,"out time","request timeout, service name is %s "),
	
	SECURITY_AUTH_TOKEN_NOT_EXIST(10,1,"token is not exsit","token is not exsit"),
	SECURITY_AUTH_TOKEN_NOT_FAIL(10,2,"token is fail %s","token is fail %s"),
	;
	
	private int order;

	private int number;

	private int code;

	private String title;

	private String message;

	private String titleChain;

	private String messageChain;

	ExceptionType(int code, String title, String message) {
		this(0, 0, title, message);
		this.code = code;
	}
	
	ExceptionType(int order, int number, String title, String message) {
		this.order = order;
		this.number = order;
		this.code = 5000 + order * 30 + number;
		this.title = title;
		this.message = message;
	}

	public ElectronResponse wrapper(ElectronRequest electronRequest, Object... message) {
		return null;
	}
	
	public ElectronResponse wrapper(ElectronRequest electronRequest,HttpResponseStatus httpResponseStatus, Object... message) {
		return electronRequest.electronResponse(httpResponseStatus, null, createMessage(message), null);
	}
	public ElectronResponse wrapper(ElectronRequest electronRequest,HttpResponseStatus httpResponseStatus,ByteBuf byteBuf,  Object... message) {
		electronRequest.electronResponse(httpResponseStatus, null, byteBuf, null);
		return null;
	}

	public ElectronResponse wrapper(ElectronRequest electronRequest,HttpResponseStatus httpResponseStatus,Throwable throwable, Object... message) {
		return electronRequest.electronResponse(httpResponseStatus, null, createMessage(message), throwable);
	}
	
	private String createMessage(Object... message) {
		return String.format(this.message, message);
	}
}
