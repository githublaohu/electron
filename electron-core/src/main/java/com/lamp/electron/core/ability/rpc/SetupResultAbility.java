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

import com.lamp.electron.base.common.ability.SetupResult;
import com.lamp.electron.base.common.annotation.AbilityAction;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.core.ability.AbstractAbility;
import com.lamp.electron.core.manage.InterfaceManage;
import com.lamp.electron.core.manage.aware.InterfaceAware;


@AbilityAction(abilityType = AbilityTypeEnum.SETUPRESULT)
public class SetupResultAbility extends AbstractAbility<SetupResult> implements InterfaceAware {

	@Override
	public void setInterfaceManage(InterfaceManage interfaceManage) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doAddAbilityObject(AbilityRelation abilityRelation, SetupResult abilityObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doRemoteAbilityObject(AbilityRelation abilityRelation) {
		// TODO Auto-generated method stub
		
	}

}
