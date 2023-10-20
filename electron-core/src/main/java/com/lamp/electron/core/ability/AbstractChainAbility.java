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

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.register.data.AbilityRelation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractChainAbility<T> extends AbstractAbility<T> implements Invoker {

	protected volatile T abilityObject;

	protected Collection<T> abilityObjectCollection;

	protected Map<Long, T> abilityObjectMap;

	protected void doAddAbilityObject(AbilityRelation abilityRelation, T abilityObject) {
		if (abilityRelation.getAbilityTypeEnum().isManyBind()) {
			if (Objects.isNull(abilityObjectMap)) {
				abilityObjectMap = new ConcurrentHashMap<>();
			}
			abilityObjectMap.put(abilityRelation.getArId(), abilityObject);
			abilityObjectCollection = abilityObjectMap.values();
		} else {
			this.abilityObject = abilityObject;
		}
	}

	protected void doRemoteAbilityObject(AbilityRelation abilityRelation) {
		if (abilityRelation.getAbilityTypeEnum().isManyBind()) {
			if (Objects.nonNull(abilityObjectMap)) {
				abilityObjectMap.remove(abilityRelation.getArId());
			}
		} else {
			this.abilityObject = null;
		}
	}

	public boolean isNotAbilityObject() {
		return Objects.isNull(abilityObject) && (Objects.isNull(abilityObjectMap) || abilityObjectMap.isEmpty());
	}

	protected Collection<T> getAbilityDataCollection(){
		return abilityObjectCollection;
	}
	
	protected T getAbilityData() {
		return abilityObject;
	}

	@Override
	public String toString() {
		return "AbstractChainAbility [abilityObject=" + abilityObject + ", abilityObjectCollection="
				+ abilityObjectCollection + ", abilityObjectMap=" + abilityObjectMap + ", abilityTypeEnum="
				+ abilityTypeEnum + ", organizationTypeEnum=" + organizationTypeEnum + "]";
	}
	
	
}
