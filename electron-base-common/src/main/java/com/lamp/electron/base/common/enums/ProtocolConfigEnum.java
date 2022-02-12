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
package com.lamp.electron.base.common.enums;

import com.lamp.electron.base.common.ability.config.http.HttpClientConfig;
import com.lamp.electron.base.common.ability.config.http.HttpRequestConfig;
import com.lamp.electron.base.common.ability.config.http.HttpServiceConfig;

public enum ProtocolConfigEnum {

	NONE(Object.class, ConfigTypeEnum.NODE, ProtocolEnum.HTTP),
	HTTP_CLIENT_CONFIG(HttpClientConfig.class, ConfigTypeEnum.CLIENT, ProtocolEnum.HTTP),
	HTTP_SERVICE_CONFIG(HttpServiceConfig.class, ConfigTypeEnum.SERVICE, ProtocolEnum.HTTP),
	HTTP_REQUSET_CONFIG(HttpRequestConfig.class, ConfigTypeEnum.REQUSET, ProtocolEnum.HTTP),
	DUBBO_REQUEST_CONFIG(HttpRequestConfig.class, ConfigTypeEnum.REQUSET, ProtocolEnum.DUBBO),
	;

	private Class<?> clazz;

	private ConfigTypeEnum configTypeEnum;

	private ProtocolEnum protocolEnum;

	ProtocolConfigEnum(Class<?> clazz, ConfigTypeEnum configTypeEnum, ProtocolEnum protocolEnum) {
		this.clazz = clazz;
		this.configTypeEnum = configTypeEnum;
		this.protocolEnum = protocolEnum;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public ConfigTypeEnum getConfigTypeEnum() {
		return configTypeEnum;
	}
	
	public ProtocolEnum getProtocolEnum() {
		return protocolEnum;
	}

	public enum ConfigTypeEnum {
		NODE, CLIENT, SERVICE, REQUSET;
	}
}
