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
package com.lamp.electron.base.common.register;

import java.util.Objects;

import com.lamp.electron.register.api.PrefixFactory;

public class ElectronPrefixFactory implements PrefixFactory {

	public static final ElectronPrefixFactory PREIFX_FACTORY = new ElectronPrefixFactory();
	
	private String basePrefix = "/electron";
	
	
	@Override
	public String createPrefix(String prefix) {
		if(Objects.isNull(prefix) || "".equals(prefix)) {
			return basePrefix;
		}
		
		StringBuffer sb = new StringBuffer(basePrefix);
		if('/' != prefix.charAt(0)) {
			sb.append('/');
		}
		sb.append(prefix);
		return sb.toString();
	}

}
