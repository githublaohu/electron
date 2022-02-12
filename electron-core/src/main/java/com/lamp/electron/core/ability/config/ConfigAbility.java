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
package com.lamp.electron.core.ability.config;

import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.perception.ConfigPerceptionFactory;
import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.core.ability.Ability;
import com.lamp.electron.core.manage.aware.ConfigPerceptionAware;

public class ConfigAbility implements Ability, ConfigPerceptionAware {

	ConfigPerceptionFactory perceptionFactory;

	@Override
	public void addAbilityObject(AbilityRelation abilityRelation) {
		OrganizationTypeEnum parentOrganizationTypeEnum = null;
		String parentId = null;
		if (OrganizationTypeEnum.INTERFACE == abilityRelation.getOrganizationTypeEnum()) {
			parentOrganizationTypeEnum = OrganizationTypeEnum.APPLICATION;
			parentId = abilityRelation.getApplicationEnglishName();
		}
		perceptionFactory.setPerceptionObject(abilityRelation.getOrganizationTypeEnum(), abilityRelation.getAbility(),
				abilityRelation.getOrganizationName(), parentOrganizationTypeEnum, parentId, null);
	}

	@Override
	public void remoteAbilityObject(AbilityRelation abilityRelation) {
		perceptionFactory.remotePerceptionObject(abilityRelation.getOrganizationTypeEnum(),
				abilityRelation.getOrganizationName(), abilityRelation.getProtocelConfigEnum().getClazz());
	}

	@Override
	public void setConfigPerceptionFactory(ConfigPerceptionFactory configPerceptionFactory) {
		this.perceptionFactory = configPerceptionFactory;
	}

}
