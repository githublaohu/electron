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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.electron.base.common.register.data.AbilityInfo;
import com.lamp.electron.console.mapper.ablility.AbilityInfoMapper;
import com.lamp.electron.console.service.ability.AbilityInfoService;

@Service
@Transactional
public class AbilityInfoServiceImpl implements AbilityInfoService{

	@Autowired
	private AbilityInfoMapper abilityInfoMapper;
	
	@Override
	public Integer insertAbilityInfo(AbilityInfo abilityInfo) {
		return abilityInfoMapper.insertAbilityInfo(abilityInfo);
	}

	@Override
	public Integer updateAbilityInfoStatus(AbilityInfo abilityInfo) {
		return abilityInfoMapper.updateAbilityInfoStatus(abilityInfo);
	}

	@Override
	public List<AbilityInfo> queryAbilityInfoByType(AbilityInfo abilityInfo) {
		return abilityInfoMapper.queryAbilityInfoByType(abilityInfo);
	}

	@Override
	public List<AbilityInfo> queryAbilityInfoByParentId(AbilityInfo abilityInfo) {
		return abilityInfoMapper.queryAbilityInfoByParentId(abilityInfo);
	}

	@Override
	public AbilityInfo queryAbilityInfoById(AbilityInfo abilityInfo) {
		return abilityInfoMapper.queryAbilityInfoById(abilityInfo);
	}

}
