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
package com.lamp.electron.core.ability.rpc;

import com.lamp.electron.base.common.ability.ResourcesRegister;
import com.lamp.electron.base.common.annotation.AbilityAction;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.core.ability.OverallSituationAbility;
import com.lamp.electron.core.manage.InterfaceManage;
import com.lamp.electron.core.manage.aware.InterfaceAware;

/**
 * 略微损失些性能没有关系，本身就是步骤功能
 * 
 * @author laohu
 *
 */
@AbilityAction(abilityType = AbilityTypeEnum.RESOURCESRESGISTER)
public class ResourcesAbility extends OverallSituationAbility<ResourcesRegister> implements InterfaceAware {

	String defaultPath;

	{
		defaultPath = ResourcesAbility.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		defaultPath = defaultPath.substring(0, defaultPath.lastIndexOf('/')) + "/resources";
	}

	protected void addBehavior(AbilityRelation abilityRelation, ResourcesRegister t, ResourcesRegister oldValue) {
		
		// 获得本地地址

		// 判断本地是否存在

		// 不存在从oss下载

		// 校验md5

		// 解压

		// 迭代压缩包

		// 创建

		//

		// LongRangeWrapper
	}

	protected void deleteBehavior(AbilityRelation abilityRelation, ResourcesRegister t) {

	}

	@Override
	public void setInterfaceManage(InterfaceManage interfaceManage) {

	}

}
