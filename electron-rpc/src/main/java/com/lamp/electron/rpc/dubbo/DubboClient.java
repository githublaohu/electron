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

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.apache.dubbo.rpc.service.GenericService;

import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.metadate.ClassificationEnum;
import com.lamp.electron.base.common.metadate.TypeMetadate;
import com.lamp.electron.base.common.register.data.InterfaceInfo;
import com.lamp.electron.rpc.api.AbstractRpcBase;
import com.lamp.electron.rpc.api.RpcHandle;

import io.netty.handler.codec.http.HttpResponseStatus;

public class DubboClient extends AbstractRpcBase implements Invoker {

	private GenericService genericService;

	public DubboClient(RpcHandle rpcHandle, GenericService genericService) {
		super(rpcHandle, null);
		this.genericService = genericService;
	}

	/**
	 * 这里好像没有办法优化处理，除非重写GenericService对象，重写dubbo整体网络协议与数据传输协议。
	 * 
	 */
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {

		InterfaceInfo interfaceInfo = (InterfaceInfo) electronRequest.networkAddress();
		Object[] parameters = new Object[interfaceInfo.getParameter().size()];
		int i = 0;
		for (TypeMetadate typeMetadate : interfaceInfo.getParameter()) {
			if (typeMetadate.getClassificationEnum() == ClassificationEnum.BASIC) {
				parameters[i++] = electronRequest.data(null, typeMetadate.getName());
			} else {
				parameters[i++] = electronRequest.data(null, typeMetadate.getClassificationEnum());
			}
		}
		CompletableFuture<Object> future = genericService.$invokeAsync(interfaceInfo.getMethodName(),
				interfaceInfo.getParameterNameArray(), parameters);
		future.whenComplete((retValue, exception) -> {
			// TODO electronResponse 没有创建 需要创建
			this.callback(electronRequest, electronRequest.electronResponse(Objects.isNull(exception)?HttpResponseStatus.OK:HttpResponseStatus.INTERNAL_SERVER_ERROR, null, retValue, exception), invoker);
		});
		return ElectronResponse.ANSY_RESPONSE;
	}

	@Override
	protected void init() {

	}

}
