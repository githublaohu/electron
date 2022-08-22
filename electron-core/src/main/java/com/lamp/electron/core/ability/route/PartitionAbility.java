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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.lamp.electron.base.common.ability.Partition;
import com.lamp.electron.base.common.annotation.AbilityAction;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.core.ability.AbstractChainAbility;
import com.lamp.electron.rpc.api.AbstractElectronBehavior;

/**
 * 隔离能力模型
 * @author jellly
 */
@AbilityAction(abilityType = AbilityTypeEnum.PARTITION)
public class PartitionAbility extends AbstractChainAbility<Partition> {

	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		for (Partition partition : getAbilityDataCollection()) {
			String value = electronRequest.data(partition.getDataSpot(), partition.getKey());
			if (partition.getValue().equals(value)) {
				AbstractElectronBehavior abstractElectronBehavior = (AbstractElectronBehavior) electronRequest;
				List<NetworkAddress> networkAddressList = abstractElectronBehavior.getNetworkAddressList();
				List<NetworkAddress> newNetworkAddressList = new ArrayList<>();
				if (Objects.isNull(partition.getVersion())) {
					for (NetworkAddress partitionNetworkAddress : partition.getInstanceInfoList()) {
						for (NetworkAddress networkAddress : networkAddressList) {
							if (Objects.equals(networkAddress.networkAddress(),
									partitionNetworkAddress.networkAddress())
									&& Objects.equals(networkAddress.port(), partitionNetworkAddress.port())) {
								newNetworkAddressList.add(networkAddress);
								break;
							}
						}
					}
				} else {
					// 区分版本
					String version = partition.getVersion();
					for (NetworkAddress networkAddress : networkAddressList) {
						if (Objects.equals(version, networkAddress.version())) {
							newNetworkAddressList.add(networkAddress);
						}
					}
				}
				if(newNetworkAddressList.isEmpty()) {
					return null;
				}
				
				abstractElectronBehavior.setNetworkAddressList(newNetworkAddressList);
				return null;
			}
		}
		return null;

	}

}
