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

import java.util.List;

import com.lamp.electron.base.common.ability.HotStandby;
import com.lamp.electron.base.common.annotation.AbilityAction;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.core.ability.AbstractChainAbility;
import com.lamp.electron.rpc.api.AbstractElectronBehavior;

/**
 * TODO
 * 热备能力模型
 * @author jellly
 */
@AbilityAction(abilityType = AbilityTypeEnum.HOT_STANDBY)
public class HotStandbyAbility extends AbstractChainAbility<HotStandby> {

	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		
		// a(1),b(1),c(2),d(3),e(4)
		// ab 路由
		// a 不存在，   bc
		AbstractElectronBehavior abstractElectronBehavior = (AbstractElectronBehavior)electronRequest;
		
		LongRangeWrapper longRangeWrapper = abstractElectronBehavior.getLongRangeWrapper();
		List<NetworkAddress> networkAddressList = longRangeWrapper.getNetworkAddress();
		
		
		
		return null;
	}

}
