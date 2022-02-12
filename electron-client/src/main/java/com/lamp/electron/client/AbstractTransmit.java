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
package com.lamp.electron.client;

import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.lamp.electron.base.common.constant.ElectronConstant;
import com.lamp.electron.client.context.ElectronContext;
import com.lamp.electron.client.transmit.TransmitFactory;

public abstract class AbstractTransmit {

	private Class<?> userClass;
	
	protected void init(ElectronProperties electronProperties) throws ClassNotFoundException {
		if(Objects.isNull(electronProperties)) {
			return;
		}
		if(Objects.isNull(electronProperties.getUserInfoClass())) {
			return;
		}
		this.userClass = Class.forName(electronProperties.getUserInfoClass());
	}
	
	protected void transmit(String userInfo) {
		if(Objects.nonNull(userInfo) && Objects.nonNull(userClass)){
			Object object = JSON.parseObject(userInfo, userClass);
			ElectronContext.getElectronContext().setUserInfo(object);
			TransmitFactory.getInstance().dransmission(ElectronConstant.USER_INFO_KEY_NAME, userInfo);
		}
	}
}
