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

import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.rpc.RpcContext;

import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.invoker.ElectronBehavior;
import com.lamp.electron.base.common.metadate.ClassificationEnum;
import com.lamp.electron.rpc.api.AbstractElectronBehavior;

import io.netty.buffer.ByteBuf;

/**
 * 挺麻烦的
 * <p>
 * 	1. 支持多参数模式
 *  2. 先实现Response
 * </p>
 * @author laohu
 *
 */
public class DubboBehavior extends AbstractElectronBehavior implements ElectronBehavior {

	private Map<String,String> attachments = new HashMap<>();
	
	public DubboBehavior( Throwable throwable) {
		super(throwable);
		Map<String,String> contextAttachments = RpcContext.getContext().getAttachments();
		attachments = new HashMap<>(contextAttachments);
		contextAttachments.clear();
		
	}


	@Override
	public ProtocolEnum getProtocolEnum() {
		return ProtocolEnum.DUBBO;
	}

	@Override
	public String data(DataSpot dataSpot, String key) {
		return null;
	}

	@Override
	public Map<String, String> data(DataSpot dataSpot) {
		return null;
	}

	@Override
	public Object data(DataSpot dataSpot, ClassificationEnum classificationEnum) {
		return null;
	}

	@Override
	public void setData(DataSpot dataSpot, String key, String value) {
		
	}

	@Override
	public boolean isResource() {
		return false;
	}

	@Override
	public ByteBuf content() {
		return null;
	}

	@Override
	public Object original() {
		return null;
	}
}
