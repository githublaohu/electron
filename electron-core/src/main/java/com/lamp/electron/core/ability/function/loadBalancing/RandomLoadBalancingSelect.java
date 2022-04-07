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
package com.lamp.electron.core.ability.function.loadBalancing;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.lamp.electron.base.common.ability.LoadBalancing;
import com.lamp.electron.base.common.enums.ability.LoadBalancingEnum;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.base.common.register.data.NetworkAddress;

/**
 * 随机负载均衡模式选择器
 * @author jellly
 */
public class RandomLoadBalancingSelect implements LoadBalancingSelect {

	@Override
	public NetworkAddress select(ElectronRequest electronRequest, LongRangeWrapper longRangeWrapper,
			LoadBalancing loadBalancing) {
		List<NetworkAddress> networkAddressList = longRangeWrapper.getNetworkAddress();
		return networkAddressList.get(ThreadLocalRandom.current().nextInt(networkAddressList.size()));
	}

	@Override
	public LoadBalancingEnum getType() {
		return LoadBalancingEnum.RANDOM;
	}

}
