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

import com.lamp.electron.base.common.ability.InstanceInfoRegister;
import com.lamp.electron.base.common.annotation.AbilityAction;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.register.data.InstanceInfo;
import com.lamp.electron.core.ability.OverallSituationAbility;
import com.lamp.electron.core.manage.InstanceManage;
import com.lamp.electron.core.manage.aware.InstanceAware;


/**
 * 实例注册模型
 * @author jellly
 */
@AbilityAction(abilityType = AbilityTypeEnum.INSTANCE_INFO_REGISTER)
public class InstanceInfoRegisterAbility extends OverallSituationAbility<InstanceInfoRegister> implements InstanceAware {

	private InstanceManage instanceManage;
		
	
	@Override
	public void setInstanceManage(InstanceManage instanceManage) {
		this.instanceManage = instanceManage;
	}

	@Override
    protected void addBehavior(InstanceInfoRegister instanceInfoRegister, InstanceInfoRegister oldInstanceInfoRegister) {
		if(Objects.nonNull(oldInstanceInfoRegister)) {
			deleteBehavior(oldInstanceInfoRegister);
		}
		instanceManage.batchRegister(instanceInfoRegister.getInstanceInfoList());
	}
	
	@Override
	protected void deleteBehavior(InstanceInfoRegister instanceInfoRegister) {
		for(InstanceInfo instanceInfo :  instanceInfoRegister.getInstanceInfoList()) {
			instanceManage.deregister(instanceInfo);
		}
	}
	
}
