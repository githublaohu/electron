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
package com.lamp.electron.console.service.ability.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.console.mapper.ablility.AbilityRelationMapper;
import com.lamp.electron.console.service.ability.AbilityRelationService;

@Service
@Transactional
public class AbilityRelationServiceImpl implements AbilityRelationService {

	private Map<Long, Object> lockObject = new ConcurrentHashMap<>();

	@Autowired
	private AbilityRelationMapper abilityRelationMapper;

	@Override
	public Integer bindAbilityRelation(AbilityRelation abilityRelation) {
		// 增加绑定次数

		// 修改绑定
		if (Objects.nonNull(abilityRelation.getArId())) {
			return abilityRelationMapper.copyAbilityRelation(abilityRelation);
		}
		// 上锁 实例上上锁
		Object object = lockObject.computeIfAbsent(abilityRelation.getOrganizationId(), key ->  new Object());
		synchronized (object) {
			return abilityRelationMapper.insertAbilityRelation(abilityRelation);
		}

	}

	@Override
	public Integer unbindAbilityRelation(AbilityRelation abilityRelation) {
		// 修改绑定次数

		// 修改绑定状态
		return abilityRelationMapper.updateAbilityInfoStatus(abilityRelation);
	}

	@Override
	public AbilityRelation queryAbilityRelationById(AbilityRelation abilityRelation) {
		return abilityRelationMapper.queryAbilityRelationById(abilityRelation);
	}

	@Override
	public List<AbilityRelation> queryAbilityRelationListByAiId(AbilityRelation abilityRelation) {
		return abilityRelationMapper.queryAbilityRelationListByAiId(abilityRelation);
	}

	@Override
	public List<AbilityRelation> queryAbilityRelationListByOrganizationId(AbilityRelation abilityRelation) {
		return abilityRelationMapper.queryAbilityRelationListByOrganizationId(abilityRelation);
	}

}
