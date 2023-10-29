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
package com.lamp.electron.base.common.ability;

import java.util.List;

import com.lamp.electron.base.common.annotation.AbilityData;
import com.lamp.electron.base.common.enums.AbilityScope;
import com.lamp.electron.base.common.enums.DataSpot;

import lombok.Data;

@Data
@AbilityData(chinaName = "条件路由", abilityScope = AbilityScope.DISCERN, overallSituation = true , childDataName = "conditions")
public class ConditionRouter {
	
	private List<Condition> conditions;
	
	@Data
	public static class Condition{
		
		private DataSpot dataSpot;
		
		private String key;
		
		private String value;
		
		private Rewrite rewrite;
		
		private String rewriteValue;
		
		private Boolean isResources;

	}
	
	public enum Rewrite{
		UNCHANGED, DELETE_KEY,REWRITE_KEY;
	}
}
