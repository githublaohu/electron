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

import javax.annotation.Resource;

import com.lamp.electron.base.common.ability.RequestRecord;
import com.lamp.electron.base.common.annotation.AbiltiyAction;
import com.lamp.electron.base.common.enums.AbilityType;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.base.common.register.data.TrafficDetails;
import com.lamp.electron.core.ability.AbstractChainAbility;
import com.lamp.electron.core.container.bean.TrafficDetailsBean;
import com.lamp.electron.rpc.api.AbstractElectronBehavior;

@AbiltiyAction(abilityType = AbilityType.REQUESTRECORD)
public class RequestRecordAbility extends AbstractChainAbility<RequestRecord> {

	@Resource
	private TrafficDetailsBean trafficDetailsBean; 
	
	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		if (abilityObject.isRecord()) {
			record(electronRequest, electronResponse);
		}
		return null;
	}

	private void record(ElectronRequest electronRequest, ElectronResponse electronResponse) {
		TrafficDetails trafficDetails = new TrafficDetails();
		AbstractElectronBehavior abstractElectronBehavior = (AbstractElectronBehavior) electronRequest;
		LongRangeWrapper longRangeWrapper = abstractElectronBehavior.getLongRangeWrapper();
		trafficDetails.setApplicationEnglishName(longRangeWrapper.getApplicationName());
		trafficDetailsBean.setTrafficDetails(trafficDetails, null, true);
	}

}
