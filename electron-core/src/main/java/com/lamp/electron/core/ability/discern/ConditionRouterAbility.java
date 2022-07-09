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
package com.lamp.electron.core.ability.discern;

import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.lamp.electron.base.common.ability.ConditionRouter;
import com.lamp.electron.base.common.ability.ConditionRouter.Condition;
import com.lamp.electron.base.common.ability.ConditionRouter.Rewrite;
import com.lamp.electron.base.common.annotation.AbilityAction;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.core.ability.OverallSituationAbility;

/**
 * 条件路由可以支持，静态资源
 * 
 * @author laohu
 *
 */
@AbilityAction(abilityType = AbilityTypeEnum.CONDITION_ROUTER)
public class ConditionRouterAbility extends OverallSituationAbility<ConditionRouter> {

	public String discern(ElectronRequest electronRequest) {
		for (Entry<String, ConditionRouter> e : this.abilityDataMap.entrySet()) {
			if (e.getValue().getConditions() == null) {
				continue;
			}
			for (Condition condition : e.getValue().getConditions()) {
				if (condition.getDataSpot() == DataSpot.URL) {
					if (!electronRequest.path().startsWith(condition.getKey())) {
						continue;
					}
					if (condition.getRewrite() != Rewrite.UNCHANGED) {
						String uri = StringUtils.substringAfter(electronRequest.path(), condition.getKey());
						if (condition.getRewrite() == Rewrite.REWRITE_KEY) {
							if (StringUtils.isNoneEmpty(condition.getRewriteValue())
									&& !Objects.equals(condition.getRewriteValue(), "/")) {
								uri = condition.getRewriteValue() + uri;
								electronRequest.path(uri);
							}
						} else {
							electronRequest.path(uri);
						}
					}
					return e.getKey();
				} else {
					String value = electronRequest.data(condition.getDataSpot(), condition.getKey());
					if(Objects.isNull(condition.getValue()) ) {
						if(StringUtils.equals(value, e.getKey())) {
							return e.getKey();
						}
					}else if (StringUtils.equals(value, condition.getValue())){
						return e.getKey();
					}
				}
			}
		}
		return null;
	}

}
