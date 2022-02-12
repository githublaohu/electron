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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.lamp.electron.base.common.annotation.AbiltiyData;
import com.lamp.electron.base.common.enums.AbiltiyScope;
import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.enums.ProtocolEnum;

import lombok.Data;

@Data
@AbiltiyData(abiltiyScope = AbiltiyScope.SECURITY, chinaName = "认证")
public class Authentication {

	private ProtocolEnum protocolEnum;

	/**
	 * 可以是redis资源名
	 */
	private String applicationName;

	/**
	 * 
	 */
	private DataSpot tokenSpot;

	/**
	 * token的获取参数名
	 */
	private String tokenName;

	/**
	 * 返回数据里面key
	 */
	private String userKey;
	
	/**
	 * 
	 */
	private DataSpot redirectSpot;

	/**
	 * 认证不通过重定向URL
	 */
	private String redirectData;

	/**
	 * 是否开启本地缓存 true是，false否
	 */
	private Boolean localCache;

	/**
	 * 
	 */
	private String cachePrefix;

	/**
	 * 本地缓存有效时间
	 */
	private Long localCacheEffectiveTime;

	/**
	 * 不需要认证的path
	 */
	private Set<String> acrossPathList;
	
	private List<AuthenticationDetails> authenticationDetailsList;
	
	public Set<String> getAcrossPathList(){
		Set<String> acrossPathList = this.acrossPathList;
		if(Objects.isNull(acrossPathList)) {
			acrossPathList = new HashSet<>();
			for(AuthenticationDetails authenticationDetails : authenticationDetailsList) {
				acrossPathList.add(authenticationDetails.getAcrossPath());
			}
			this.acrossPathList = acrossPathList;
		}
		return acrossPathList;
	}
	
	@Data
	static class AuthenticationDetails{
		
		private String acrossPath;
	}
}
