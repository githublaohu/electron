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
package com.lamp.electron.base.common.enums;

import com.lamp.electron.base.common.ability.Alarm;
import com.lamp.electron.base.common.ability.Authentication;
import com.lamp.electron.base.common.ability.ConditionRouter;
import com.lamp.electron.base.common.ability.DataInjection;
import com.lamp.electron.base.common.ability.ErrorResult;
import com.lamp.electron.base.common.ability.InstanceInfoRegister;
import com.lamp.electron.base.common.ability.HotStandby;
import com.lamp.electron.base.common.ability.InterfaceRegister;
import com.lamp.electron.base.common.ability.Interfaces;
import com.lamp.electron.base.common.ability.Invoking;
import com.lamp.electron.base.common.ability.LinkTrack;
import com.lamp.electron.base.common.ability.LoadBalancing;
import com.lamp.electron.base.common.ability.MessageMiddlewareRPC;
import com.lamp.electron.base.common.ability.ParamVerification;
import com.lamp.electron.base.common.ability.Partition;
import com.lamp.electron.base.common.ability.RedisRPC;
import com.lamp.electron.base.common.ability.RequestRecord;
import com.lamp.electron.base.common.ability.ResourcesRegister;
import com.lamp.electron.base.common.ability.Seckill;
import com.lamp.electron.base.common.ability.SetupResult;
import com.lamp.electron.base.common.ability.Statistics;
import com.lamp.electron.base.common.ability.Template;
import com.lamp.electron.base.common.ability.TrafficSafety;
import com.lamp.electron.base.common.ability.config.Config;
import com.lamp.electron.base.common.annotation.AbilityData;

/**
 * <P>
 * 1. 先按队列模型处理，在按照动态模型处理。
 * </P>
 * <P>
 * 2. 动态模型处理，成本很大
 * </P>
 * <P>
 * 3. 不建议大家，在动态模型没有出来的时候，自己添加动作
 * </P>
 * 
 * @author laohu
 *
 */
public enum AbilityTypeEnum {
	
	
	STATISTICS(Statistics.class),
	REQUEST_RECORD(RequestRecord.class),
	LINK_TRACK(LinkTrack.class),
	
	INTERFACE_REGISTER(InterfaceRegister.class),
	RESOURCES_REGISTER(ResourcesRegister.class),
	INSTANCE_INFO_REGISTER(InstanceInfoRegister.class),

	CONDITION_ROUTER(ConditionRouter.class),
	
	HOT_STANDBY(HotStandby.class),
	PARTITION(Partition.class),
//	GRAYSCALE(),
	LOAD_BALANCING(LoadBalancing.class),

	SEC_KILL(Seckill.class),
	TRAFFIC_SAFETY(TrafficSafety.class),
	PARAM_VERIFICATION(ParamVerification.class),
	AUTHENTICATION(Authentication.class),

	INVOKING(Invoking.class),
	
	ERROR_RESULT(ErrorResult.class),
	ALARM(Alarm.class),
	
	DATAINJECTION(DataInjection.class),
	
	
	CONFIG(Config.class),
	
	
	
	REDIS_RPC(RedisRPC.class),
	MESSAGE_MIDDLEWARE_RPC(MessageMiddlewareRPC.class),
	TEMPLATE(Template.class),
	INTERFACES(Interfaces.class),
	SETUP_RESULT(SetupResult.class),
	;

	
	private AbilityData abilityData;
	
	private Class<?> clazz;
	

	AbilityTypeEnum(Class<?> clazz) {
		this.clazz = clazz;
		abilityData = clazz.getAnnotation(AbilityData.class);
	}
	
	public AbilityScope[] getAbilityScope() {
		return abilityData.abilityScope();
	}

	public String getName() {
		return clazz.getSimpleName();
	}

	public Class<?> getAbilityObject() {
		return clazz;
	}
	
	public String getChinaName() {
		return abilityData.chinaName();
	}

	public boolean isManyBind() {
		return abilityData.manyBind();
	}

	public boolean isOverallSituation() {
		return abilityData.overallSituation();
	}
	
	public boolean isExecuteMany() {
		return abilityData.executeMany();
	}
	
	public OrganizationTypeEnum[] getAbilityBindRelation() {
		return abilityData.abilityBindRelation();
	}

	public String getExplain() {
		return abilityData.explain();
	}

	public String getEffect() {
		return abilityData.effect();
	}

	public String getChildDataName() {
		return abilityData.childDataName();
	}
	
	public String getRetrySpot() {
		return null;
	}
	
	public OrganizationTypeEnum[] getOrganizationTypeEnum() {
		return abilityData.abilityBindRelation();
	}

}
