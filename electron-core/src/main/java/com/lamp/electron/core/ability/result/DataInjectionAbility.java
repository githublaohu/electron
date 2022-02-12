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
package com.lamp.electron.core.ability.result;

import com.lamp.electron.base.common.ability.DataInjection;
import com.lamp.electron.base.common.ability.DataInjection.ResultDataDisposeInfo;
import com.lamp.electron.base.common.annotation.AbiltiyAction;
import com.lamp.electron.base.common.enums.AbilityType;
import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.enums.EffectPoint;
import com.lamp.electron.base.common.invoker.ElectronBehavior;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.core.ability.AbstractChainAbility;
import com.lamp.electron.core.ability.extend.ResultAbility;

/**
 * 
 * @author laohu
 *
 */
@AbiltiyAction(abilityType = AbilityType.DATAINJECTION, effectPoint = EffectPoint.RESULT)
public class DataInjectionAbility extends AbstractChainAbility<DataInjection> implements ResultAbility {

	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		dataDispose(electronRequest, electronResponse, DataSpot.REQUEST);
		return null;
	}

	@Override
	public ElectronResponse result(ElectronRequest electronRequest, ElectronResponse electronResponse,
			Invoker invoker) {
		dataDispose(electronRequest, electronResponse, DataSpot.RESPONSE);
		return null;
	}

	private void dataDispose(ElectronRequest electronRequest,ElectronResponse electronResponse,DataSpot regionSpot) {
		for(ResultDataDisposeInfo resultDataDisposeInfo : abilityObject.getResultDataDisposeInfo() ) {
			ElectronBehavior electronBehavior = resultDataDisposeInfo.getRegionSpot()==DataSpot.REQUEST?electronRequest:electronResponse;
			if(resultDataDisposeInfo.getOperateSpot() == DataSpot.APPEND) {
				electronBehavior.setData(resultDataDisposeInfo.getDataSpot(), resultDataDisposeInfo.getKey(), resultDataDisposeInfo.getValue());
			}else if(resultDataDisposeInfo.getOperateSpot() == DataSpot.DELETE) {
				
			}
		}
	}
}
