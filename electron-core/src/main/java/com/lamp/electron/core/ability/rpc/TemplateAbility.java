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

import com.lamp.electron.base.common.ability.Template;
import com.lamp.electron.base.common.annotation.AbiltiyAction;
import com.lamp.electron.base.common.enums.AbilityType;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.core.ability.AbstractChainAbility;
import com.lamp.electron.core.manage.InterfaceManage;
import com.lamp.electron.core.manage.aware.InterfaceAware;

@AbiltiyAction(abilityType = AbilityType.TEMPLATE)
public class TemplateAbility extends AbstractChainAbility<Template> implements InterfaceAware{

	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInterfaceManage(InterfaceManage interfaceManage) {
		// TODO Auto-generated method stub
		
	}

}
