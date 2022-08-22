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
import com.lamp.electron.base.common.ability.CacheConfig;
import com.lamp.electron.base.common.ability.ConditionRouter;
import com.lamp.electron.base.common.ability.DataInjection;
import com.lamp.electron.base.common.ability.ErrerResult;
import com.lamp.electron.base.common.ability.ExampleInfoRegister;
import com.lamp.electron.base.common.ability.HotStandby;
import com.lamp.electron.base.common.ability.InterfaceRegister;
import com.lamp.electron.base.common.ability.Interfaces;
import com.lamp.electron.base.common.ability.Invoking;
import com.lamp.electron.base.common.ability.LinkTrack;
import com.lamp.electron.base.common.ability.LoadBalancing;
import com.lamp.electron.base.common.ability.MessageMiddlewareRPC;
import com.lamp.electron.base.common.ability.ParemVerifcation;
import com.lamp.electron.base.common.ability.Partition;
import com.lamp.electron.base.common.ability.RedisRPC;
import com.lamp.electron.base.common.ability.RequestRecord;
import com.lamp.electron.base.common.ability.ResourcesRegister;
import com.lamp.electron.base.common.ability.Seckill;
import com.lamp.electron.base.common.ability.SetupResult;
import com.lamp.electron.base.common.ability.Statistics;
import com.lamp.electron.base.common.ability.Template;
import com.lamp.electron.base.common.ability.TraffisSafety;
import com.lamp.electron.base.common.ability.config.Config;
import com.lamp.electron.base.common.annotation.AbiltiyData;

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
public enum AbilityType {
	
	
	STATISTICS(Statistics.class),
	REQUESTRECORD(RequestRecord.class),
	LINKTRACK(LinkTrack.class),
	
	INTERFACERESGISTER(InterfaceRegister.class),
	RESOURCESRESGISTER(ResourcesRegister.class),
	EXAMPLEINFOREGISTER(ExampleInfoRegister.class),

	CONDITIONROUTE(ConditionRouter.class),
	
	HOTSTANDBY(HotStandby.class),
	PARTITION(Partition.class),
//	GRAYSCALE(),
	LOADBALANCING(LoadBalancing.class),

	SECKILL(Seckill.class),
	TRAFFISSAFETY(TraffisSafety.class),
	PAREMVERIFCATION(ParemVerifcation.class),
	AUTHENTICATION(Authentication.class),

	CACHE(CacheConfig.class),
	INVOKING(Invoking.class),
	
	ERRERRESULT(ErrerResult.class),
	ALARM(Alarm.class),
	
	DATAINJECTION(DataInjection.class),
	
	
	CONFIG(Config.class),
	
	
	
	REDISRPC(RedisRPC.class),
	MESSAGE_MIDDLEWARE_RPC(MessageMiddlewareRPC.class),
	TEMPLATE(Template.class),
	INTERFACES(Interfaces.class),
	SETUPRESULT(SetupResult.class),
	;

	
	private AbiltiyData abiltiyData;
	
	private Class<?> clazz;
	

	AbilityType(Class<?> clazz) {
		this.clazz = clazz;
		abiltiyData = clazz.getAnnotation(AbiltiyData.class);
	}
	
	public AbiltiyScope[] getAbiltiyScope() {
		return abiltiyData.abiltiyScope();
	}

	public String getName() {
		return clazz.getSimpleName();
	}

	public Class<?> getAbiltiyObject() {
		return clazz;
	}
	
	public String getChildName() {
		return abiltiyData.chinaName();
	}

	public boolean isManyBind() {
		return abiltiyData.manyBind();
	}

	public boolean isOverallSituation() {
		return abiltiyData.overallSituation();
	}
	
	public boolean isExecuteMany() {
		return abiltiyData.executeMany();
	}
	
	public OrganizationTypeEnum[] getAbiltityBindRelation() {
		return abiltiyData.abiltityBindRelation();
	}

	public String getExplain() {
		return abiltiyData.explain();
	}

	public String getEffect() {
		return abiltiyData.effect();
	}

	public String getChildDataName() {
		return abiltiyData.childDataName();
	}
	
	public String getRetrySpot() {
		return null;
	}
	
	public OrganizationTypeEnum[] getOrganizationTypeEnum() {
		return abiltiyData.abiltityBindRelation();
	}

}
