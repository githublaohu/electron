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

import com.lamp.electron.base.common.annotation.AbiltiyData;
import com.lamp.electron.base.common.enums.AbiltiyScope;

import lombok.Data;

/**
 * json {"code":"" ,message:"",connect:""}
 * 		 {"{codeKey}":"" ,{messageKey}:"",{connectKey}:""}
 * templateContent  失败代码：{code}，失败信息：{message}
 *         失败代码：5000，失败信息：未知
 * @author laohu
 *
 */
@Data
@AbiltiyData(abiltiyScope = AbiltiyScope.ERRER, chinaName = "异常返回")
public class ErrerResult {

	private Integer basicsCode;

	private ResultMode resultMode;

	private String codeKey;

	private String messageKey;

	private String connectKey;

	private String templateContent;
	
	enum ResultMode{
		
		JSON,
		TEMPLATE
	}
}
