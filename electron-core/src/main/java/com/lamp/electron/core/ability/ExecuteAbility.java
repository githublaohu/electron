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

import java.util.List;
import java.util.Objects;

import com.lamp.electron.base.common.enums.AbilityType;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecuteAbility implements Invoker {

	protected volatile ExecuteAbility nextAbility;
	
	private ExecuteAbility retrySpotAbility;

	private AbstractChainAbility<Object> abstractChainAbility;

	private List<AbstractChainAbility<Object>> abstractChainAbilityList;
	
	private AbilityType abilityTypeEnum;


	public ExecuteAbility(AbstractChainAbility<Object> abstractChainAbility,ExecuteAbility nextAbility) {
		this.abstractChainAbility = abstractChainAbility;
		this.abilityTypeEnum = abstractChainAbility.getAbilityTypeEnum();
		this.nextAbility = nextAbility;
	}
	
	public ExecuteAbility(List<AbstractChainAbility<Object>> abstractChainAbilityList,ExecuteAbility nextAbility,ExecuteAbility retrySpotAbility) {
		if(abstractChainAbilityList.size() == 1) {
			this.abstractChainAbility = abstractChainAbilityList.get(0);
		}else {
			this.abstractChainAbilityList = abstractChainAbilityList;
		}
		this.abilityTypeEnum = abstractChainAbilityList.get(0).getAbilityTypeEnum();
		this.nextAbility = nextAbility;
		this.retrySpotAbility = retrySpotAbility;
	}

	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse,
			Invoker abilityInvoker) {

		if (Objects.nonNull(this.abstractChainAbility)) {
			if(log.isTraceEnabled()) {
				log.trace(abstractChainAbility.toString());
			}
			if (abstractChainAbility.isNotAbilityObject()) {
				return null;
			}
			return abstractChainAbility.run(electronRequest, electronResponse, abilityInvoker);
		} else {
			ElectronResponse newElectronResponse = null;
			for (AbstractChainAbility<Object> abstractChainAbility : abstractChainAbilityList) {
				if(log.isTraceEnabled()) {
					log.trace(abstractChainAbility.toString());
				}
				if (abstractChainAbility.isNotAbilityObject()) {
					continue;
				}
				newElectronResponse = abstractChainAbility.run(electronRequest, electronResponse,
						abilityInvoker);
				// 如果需要全连执行，就结束
				if (Objects.nonNull(newElectronResponse) && abilityTypeEnum.isExecuteMany()) {
					break;
				}
			}
			return newElectronResponse;
		}
	}
	
	public ExecuteAbility getNextAbility() {
		return nextAbility;
	}

	public void setNextAbility(ExecuteAbility nextAbility) {
		this.nextAbility = nextAbility;
	}
		
	public ExecuteAbility getRetrySpotAbility() {
		return retrySpotAbility;
	}

	public void setRetrySpotAbility(ExecuteAbility retrySpotAbility) {
		this.retrySpotAbility = retrySpotAbility;
	}
}
