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
package com.lamp.electron.core.ability.rpc;

import java.util.concurrent.atomic.AtomicLong;

import com.lamp.electron.base.common.ability.MessageMiddlewareRPC;
import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.annotation.AbilityAction;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.base.common.register.data.InstanceInfo;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.core.ability.Ability;
import com.lamp.electron.core.manage.InterfaceManage;
import com.lamp.electron.core.manage.aware.ElectronClientAware;
import com.lamp.electron.core.manage.aware.InterfaceAware;
import com.lamp.electron.rpc.ElectronClientFactory;

@AbilityAction(abilityType = AbilityTypeEnum.MESSAGE_MIDDLEWARE_RPC)
public class MessageMiddlewareRPCAbility implements Ability, InterfaceAware, ElectronClientAware {

	private InterfaceManage interfaceManage;

	private ElectronClientFactory electronClientFactory;

	private Perception<RpcRequestConfig> perception = new Perception<>();

	@Override
	public void setInterfaceManage(InterfaceManage interfaceManage) {
		this.interfaceManage = interfaceManage;
	}

	@Override
	public void setElectronClientFactory(ElectronClientFactory electronClientFactory) {
		this.electronClientFactory = electronClientFactory;
	}

	@Override
	public synchronized void addAbilityObject(AbilityRelation abilityRelation) {
		MessageMiddlewareRPC messageMiddlewareRPC = (MessageMiddlewareRPC) abilityRelation.getAbility();
		perception.setPereptionObject(messageMiddlewareRPC);

		LongRangeWrapper longRangeWrapper = new LongRangeWrapper(abilityRelation.getOrganizationName(),
				abilityRelation.getApplicationName(), new AtomicLong());
		for (String networkAddressName : messageMiddlewareRPC.getNetworkAddressName()) {
			InstanceInfo instanceInfo = new InstanceInfo();
			instanceInfo.setName(networkAddressName);
			instanceInfo.setNetworkAddress(networkAddressName);
			electronClientFactory.createFactory(instanceInfo, null);
			instanceInfo.setInvoker(electronClientFactory.createRpcClient(instanceInfo, null, perception));
			longRangeWrapper.addNetworkAddress(instanceInfo);
		}
		interfaceManage.register(longRangeWrapper);

	}

	@Override
	public void remoteAbilityObject(AbilityRelation abilityRelation) {
		interfaceManage.unRegister(abilityRelation.getOrganizationName());
	}

}
