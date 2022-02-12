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
package com.lamp.electron.console.controller.ability;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.console.service.ability.AbilityRelationService;

@RestController
@RequestMapping("/abilityRelation")
public class AbilityRelationController {

	@Autowired
	private AbilityRelationService abilityRelationService;

	@PostMapping("bindAbilityRelation")
	public Integer bindAbilityRelation(@RequestBody AbilityRelation abilityRelation) {
		return abilityRelationService.bindAbilityRelation(abilityRelation);
	}

	@PostMapping("unbindAbilityRelation")
	public Integer unbindAbilityRelation(@RequestBody AbilityRelation abilityRelation) {
		return abilityRelationService.unbindAbilityRelation(abilityRelation);
	}

	@PostMapping("queryAbilityRelationById")
	public AbilityRelation queryAbilityRelationById(@RequestBody AbilityRelation abilityRelation) {
		return abilityRelationService.queryAbilityRelationById(abilityRelation);
	}

	@PostMapping("queryAbilityRelationListByAiId")
	public List<AbilityRelation> queryAbilityRelationListByAiId(@RequestBody AbilityRelation abilityRelation) {
		return abilityRelationService.queryAbilityRelationListByAiId(abilityRelation);
	}
	
	@PostMapping("queryAbilityRelationListByOrganizationId")
	public List<AbilityRelation> queryAbilityRelationListByOrganizationId(@RequestBody AbilityRelation abilityRelation) {
		return abilityRelationService.queryAbilityRelationListByOrganizationId(abilityRelation);
	}
}
