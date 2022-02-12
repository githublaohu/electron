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
package com.lamp.electron.core.ability;

import java.util.Objects;

import com.lamp.electron.base.common.enums.AbilityType;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.register.data.AbilityRelation;

import lombok.Data;

@Data
public abstract class AbstractAbility<T> implements Ability{

	protected AbilityType abilityTypeEnum;
	
	protected OrganizationTypeEnum organizationTypeEnum;
	
	
	@SuppressWarnings("unchecked")
	public  void addAbilityObject(AbilityRelation abilityRelation ) {
		if(Objects.isNull(abilityTypeEnum)) {
			this.abilityTypeEnum = abilityRelation.getAbilityTypeEnum();
			this.organizationTypeEnum = abilityRelation.getOrganizationTypeEnum();
		}
		doAddAbilityObject(abilityRelation, (T)abilityRelation.getAbility());
	}
	
	
	public  void remoteAbilityObject(AbilityRelation abilityRelation) {
		doRemoteAbilityObject(abilityRelation);
	} 

	
	protected  abstract void doAddAbilityObject(AbilityRelation abilityRelation , T abilityObject);
	
	
	protected  abstract void doRemoteAbilityObject(AbilityRelation abilityRelation);
}

