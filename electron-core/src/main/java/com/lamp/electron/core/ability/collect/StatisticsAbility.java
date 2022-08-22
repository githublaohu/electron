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
package com.lamp.electron.core.ability.collect;

import java.util.Objects;

import javax.annotation.Resource;

import com.alibaba.csp.sentinel.slots.statistic.metric.ArrayMetric;
import com.lamp.electron.base.common.ability.Statistics;
import com.lamp.electron.base.common.annotation.AbiltiyAction;
import com.lamp.electron.base.common.enums.AbilityType;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.core.ability.AbstractChainAbility;
import com.lamp.electron.core.ability.extend.ResultAbility;
import com.lamp.electron.core.ability.function.statistics.StatisticsCentre;

@AbiltiyAction(abilityType = AbilityType.STATISTICS)
public class StatisticsAbility extends AbstractChainAbility<Statistics> implements ResultAbility{

	@Resource
	private StatisticsCentre statisticsCentre;
	
	private ArrayMetric arrayMetric;
	
	public boolean isNotAbilityObject() {
		return true;
	}
	
	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		if(Objects.isNull(arrayMetric)) {
			
		}
		arrayMetric.addPass(1);
		return null;
	}

	@Override
	public ElectronResponse result(ElectronRequest electronRequest, ElectronResponse electronResponse,
			Invoker invoker) {
		// 被拒绝动作拒绝的，比如限流，认证失败，数据校验失败
		if(Objects.isNull(electronResponse.throwable())) {
			arrayMetric.addSuccess(1);
		}else {
			arrayMetric.addException(1);
		}
		arrayMetric.getSampleCount();
		return null;
	}


}
