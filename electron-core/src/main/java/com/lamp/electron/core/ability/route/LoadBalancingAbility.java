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
package com.lamp.electron.core.ability.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lamp.electron.base.common.ability.LoadBalancing;
import com.lamp.electron.base.common.annotation.AbilityAction;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.enums.ability.LoadBalancingEnum;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.core.ability.AbstractChainAbility;
import com.lamp.electron.core.ability.function.loadBalancing.LoadBalancingSelect;
import com.lamp.electron.core.ability.function.loadBalancing.RandomLoadBalancingSelect;
import com.lamp.electron.rpc.api.AbstractElectronBehavior;

@AbilityAction(abilityType = AbilityTypeEnum.LOADBALANCING)
public class LoadBalancingAbility extends AbstractChainAbility<LoadBalancing>{

	private static final Map<LoadBalancingEnum, LoadBalancingSelect>  loadBalancingSelectMap = new HashMap<>();
	
	static{
		loadBalancingSelectMap.put(LoadBalancingEnum.RANDOM, new RandomLoadBalancingSelect());
	}
	
	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		
		AbstractElectronBehavior abstractElectronBehavior = (AbstractElectronBehavior)electronRequest;
		
		LongRangeWrapper longRangeWrapper = abstractElectronBehavior.getLongRangeWrapper();
		List<NetworkAddress> networkAddressList = longRangeWrapper.getNetworkAddress();
		if(networkAddressList.isEmpty()) {
			return null;
		}
		NetworkAddress networkAddress;
		if(networkAddressList.size() == 1) {
			networkAddress = networkAddressList.get(0);
		}else {
			LoadBalancingSelect balancingSelect = loadBalancingSelectMap.get(abilityObject.getName());
			networkAddress = balancingSelect.select(electronRequest, longRangeWrapper, abilityObject);
		}
		abstractElectronBehavior.setNetworkAddress(networkAddress);
		return null;
	}


}
