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

import com.lamp.electron.base.common.annotation.AbilityData;
import com.lamp.electron.base.common.enums.AbilityScope;
import com.lamp.electron.base.common.viewmodel.BehaviorOperation;
import com.lamp.electron.base.common.viewmodel.BehaviorType;
import com.lamp.electron.base.common.viewmodel.DataModeBehavior;
import com.lamp.electron.base.common.viewmodel.ViewModel;

import lombok.Data;

/**
 * 类似于nginx的静态资源代理，希望有nginx的功能从而替代nginx，减少组件，简短链路长度
 * 
 * @author laohu
 *
 */
@Data
@ViewModel(slaveName = "resourcesInfo")
@AbilityData(chinaName = "资源注册", abilityScope = AbilityScope.REGISTER, childDataName = "resourcesInfo")
public class ResourcesRegister {

	@BehaviorOperation(update = false, query = false, queryForm = false)
	@DataModeBehavior(behaviorType = BehaviorType.ALL, title = "首页路径",isColumn = true)
	private String index;

	@BehaviorOperation(update = false, query = false, queryForm = false)
	@DataModeBehavior(behaviorType = BehaviorType.ALL, title = "ico",isColumn = true)
	private String faviconIco;

	private List<ResourcesInfo> resourcesInfo;
	

	@Data
	@ViewModel
	public static class ResourcesInfo {

		@BehaviorOperation(update = false, query = false, queryForm = false)
		@DataModeBehavior(behaviorType = BehaviorType.ALL, title = "下载地址",isColumn = true)
		private String remoteAddress;

		@BehaviorOperation(update = false, query = false, queryForm = false)
		@DataModeBehavior(behaviorType = BehaviorType.ALL, title = "请求前缀",isColumn = true)
		private String prefix;

		private String MD5;
		
		@BehaviorOperation(update = false, query = false, queryForm = false)
		@DataModeBehavior(behaviorType = BehaviorType.ALL, title = "版本号",isColumn = true)
		private String version;
	}
}
