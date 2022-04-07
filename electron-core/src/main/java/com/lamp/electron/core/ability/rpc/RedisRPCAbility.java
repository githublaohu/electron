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

import com.lamp.electron.base.common.ability.RedisRPC;
import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.annotation.AbilityAction;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.base.common.register.data.InstanceInfo;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.core.ability.AbstractAbility;
import com.lamp.electron.core.manage.InterfaceManage;
import com.lamp.electron.core.manage.aware.ElectronClientAware;
import com.lamp.electron.core.manage.aware.InterfaceAware;
import com.lamp.electron.rpc.ElectronClientFactory;

@AbilityAction(abilityType = AbilityTypeEnum.REDIS_RPC)
public class RedisRPCAbility extends AbstractAbility<RedisRPC> implements InterfaceAware, ElectronClientAware {

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
		RedisRPC redisRPC = (RedisRPC) abilityRelation.getAbility();
		perception.setPereptionObject(redisRPC);

		LongRangeWrapper longRangeWrapper = new LongRangeWrapper(abilityRelation.getOrganizationName(),
				abilityRelation.getApplicationName(), new AtomicLong());
		InstanceInfo instanceInfo = new InstanceInfo();
		instanceInfo.setName(null);
		instanceInfo.setNetworkAddress(redisRPC.getNetworkAddressName());
		electronClientFactory.createFactory(instanceInfo, null);
		instanceInfo.setInvoker(electronClientFactory.createRpcClient(instanceInfo, null, perception));
		longRangeWrapper.addNetworkAddress(instanceInfo);
		interfaceManage.register(longRangeWrapper);

	}

	@Override
	public void remoteAbilityObject(AbilityRelation abilityRelation) {
		interfaceManage.unRegister(abilityRelation.getOrganizationName());
	}

	@Override
	protected void doAddAbilityObject(AbilityRelation abilityRelation, RedisRPC abilityObject) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doRemoteAbilityObject(AbilityRelation abilityRelation) {
		// TODO Auto-generated method stub

	}

}
