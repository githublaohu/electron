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
package com.lamp.electron.base.common.ability;

import java.util.List;

import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.annotation.AbiltiyData;
import com.lamp.electron.base.common.enums.AbiltiyScope;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AbiltiyData(abiltiyScope = AbiltiyScope.RPC, chinaName = "MessageMiddlewareRPC", abiltityBindRelation = OrganizationTypeEnum.INTERFACE)
public class MessageMiddlewareRPC extends RpcRequestConfig {

	private MessageMiddlewareSendType sendType;

	private String inventedPath;

	private String topic;

	private String tags;

	private String key;

	private String successData;

	private String exceptionData;

	private String log;

	private Long timeout;

	private List<String> networkAddressName;

	public enum MessageMiddlewareSendType {
		ONEWAY, ASYNC, RUQUEST;
	}

}
