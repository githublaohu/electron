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
package com.lamp.electron.core.ability.discern;

import java.util.Objects;

import com.lamp.electron.base.common.ability.ExampleInfoRegister;
import com.lamp.electron.base.common.annotation.AbiltiyAction;
import com.lamp.electron.base.common.enums.AbilityType;
import com.lamp.electron.base.common.register.data.ExampleInfo;
import com.lamp.electron.core.ability.OverallSituationAbility;
import com.lamp.electron.core.manage.ExampleManage;
import com.lamp.electron.core.manage.aware.ExampleAware;


@AbiltiyAction(abilityType = AbilityType.EXAMPLEINFOREGISTER)
public class ExampleinfoRegisterAbility extends OverallSituationAbility<ExampleInfoRegister> implements ExampleAware{

	private ExampleManage exampleManage;
		
	
	@Override
	public void setExampleManage(ExampleManage exampleManage) {
		this.exampleManage = exampleManage;
	}

	protected void addBehavior(ExampleInfoRegister exampleinfoRegister,ExampleInfoRegister oldExampleinfoRegister) {
		if(Objects.nonNull(oldExampleinfoRegister)) {
			deleteBehavior(oldExampleinfoRegister);
		}
		exampleManage.batchRegister(exampleinfoRegister.getExampleInfoList());
	}
	
	protected void deleteBehavior(ExampleInfoRegister exampleinfoRegister) {
		for(ExampleInfo exampleInfo :  exampleinfoRegister.getExampleInfoList()) {
			exampleManage.unRegister(exampleInfo);
		}
	}
	
}
