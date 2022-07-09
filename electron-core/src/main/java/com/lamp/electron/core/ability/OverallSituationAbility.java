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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.electron.base.common.register.data.AbilityRelation;

/**
 * 全局策略实体
 * @author jellly
 */
public abstract class OverallSituationAbility<T> extends AbstractAbility<T> {

	protected Map<String, T> abilityDataMap = new ConcurrentHashMap<>();

	@Override
	protected void doAddAbilityObject(AbilityRelation abilityRelation, T t) {
		this.addBehavior(abilityRelation,t, abilityDataMap.put(abilityRelation.getOrganizationName(), t));

	}

	protected void addBehavior(AbilityRelation abilityRelation,T t, T oldValue) {
		this.addBehavior(t,oldValue);
	}

	protected void deleteBehavior(AbilityRelation abilityRelation,T t) {
		this.deleteBehavior(t);
	}

	protected void addBehavior(T t, T oldValue) {

	}

	protected void deleteBehavior(T t) {

	}

	@Override
	protected void doRemoteAbilityObject(AbilityRelation abilityRelation) {
		this.deleteBehavior(abilityRelation,abilityDataMap.remove(abilityRelation.getOrganizationName()));
	}

}
