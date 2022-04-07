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

import com.lamp.electron.base.common.ability.InterfaceRegister;
import com.lamp.electron.base.common.register.data.InterfaceInfo;
import com.lamp.electron.core.ability.OverallSituationAbility;
import com.lamp.electron.core.manage.InterfaceManage;
import com.lamp.electron.core.manage.aware.InterfaceAware;

public class InterfaceRegisterAbility extends OverallSituationAbility<InterfaceRegister> implements InterfaceAware {

	private InterfaceManage interfaceManage;

	@Override
	public void setInterfaceManage(InterfaceManage interfaceManage) {
		this.interfaceManage = interfaceManage;

	}

	@Override
    protected void addBehavior(InterfaceRegister t, InterfaceRegister oldValue) {
		if(Objects.nonNull(oldValue)) {
			this.deleteBehavior(oldValue);
		}
		interfaceManage.batchRegister(t.getInterfaceInfoList());
	}

	@Override
	protected void deleteBehavior(InterfaceRegister t) {
		for(InterfaceInfo interfaceInfo : t.getInterfaceInfoList()) {
			interfaceManage.deregister(interfaceInfo);
		}
	}

}
