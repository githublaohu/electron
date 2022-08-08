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
package com.lamp.electron.console.service.ability;

import java.util.List;

import com.lamp.electron.base.common.register.data.AbilityRelation;

/**
 * 能力策略Service
 * @author jellly
 */
public interface AbilityRelationService {

	/**
	 * 绑定能力
	 * @param abilityRelation
	 * @return
	 */
	public Integer bindAbilityRelation(AbilityRelation abilityRelation);

	/**
	 * 解绑能力
	 * @param abilityRelation
	 * @return
	 */
	public Integer unbindAbilityRelation(AbilityRelation abilityRelation);

	/**
	 * 查询能力
	 * @param abilityRelation
	 * @return
	 */
	public AbilityRelation queryAbilityRelationById(AbilityRelation abilityRelation);

	/**
	 * 根据策略ID查询能力
	 * @param abilityRelation
	 * @return
	 */
	public List<AbilityRelation> queryAbilityRelationListByAiId(AbilityRelation abilityRelation);

	/**
	 * 根据组织ID查询能力列表
	 * @param abilityRelation
	 * @return
	 */
	public List<AbilityRelation> queryAbilityRelationListByOrganizationId(AbilityRelation abilityRelation);
}
