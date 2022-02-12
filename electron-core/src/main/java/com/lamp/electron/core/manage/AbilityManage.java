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
package com.lamp.electron.core.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.alibaba.fastjson.JSONObject;
import com.lamp.electron.base.common.ability.Authentication;
import com.lamp.electron.base.common.ability.LoadBalancing;
import com.lamp.electron.base.common.enums.AbilityType;
import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.enums.EffectPoint;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.enums.ability.LoadBalancingEnum;
import com.lamp.electron.base.common.perception.ConfigPerceptionFactory;
import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.base.common.register.server.AbilityRelationRegister;
import com.lamp.electron.core.ability.Ability;
import com.lamp.electron.core.ability.AbstractAbility;
import com.lamp.electron.core.ability.AbstractChainAbility;
import com.lamp.electron.core.ability.ExecuteAbility;
import com.lamp.electron.core.ability.collect.RequestRecordAbility;
import com.lamp.electron.core.ability.collect.StatisticsAbility;
import com.lamp.electron.core.ability.config.ConfigAbility;
import com.lamp.electron.core.ability.discern.ConditionAbility;
import com.lamp.electron.core.ability.discern.ExampleinfoRegisterAbility;
import com.lamp.electron.core.ability.discern.InterfaceRegisterAbiltiy;
import com.lamp.electron.core.ability.discern.ResourcesRegisterAbility;
import com.lamp.electron.core.ability.error.ErrorResultAbiliby;
import com.lamp.electron.core.ability.extend.ErrorAbility;
import com.lamp.electron.core.ability.extend.ErrorAbilityWrapper;
import com.lamp.electron.core.ability.extend.PostAbility;
import com.lamp.electron.core.ability.extend.PostAbilityWrapper;
import com.lamp.electron.core.ability.extend.ResultAbility;
import com.lamp.electron.core.ability.extend.ResultAbilityWrapper;
import com.lamp.electron.core.ability.invoking.InvokingAbility;
import com.lamp.electron.core.ability.result.AlarmAbility;
import com.lamp.electron.core.ability.result.DataInjectionAbility;
import com.lamp.electron.core.ability.route.HotStandbyAbility;
import com.lamp.electron.core.ability.route.LoadBalancingAbiltiy;
import com.lamp.electron.core.ability.route.PartitionAbility;
import com.lamp.electron.core.ability.security.AuthenticationAbility;
import com.lamp.electron.core.ability.security.ParemVerifcationAbility;
import com.lamp.electron.core.ability.security.SeckillAbility;
import com.lamp.electron.core.ability.security.TraffisSafetyAbility;
import com.lamp.electron.core.container.ContainerBeanFactory;
import com.lamp.electron.core.manage.aware.ConfigPerceptionAware;
import com.lamp.electron.core.manage.aware.ExampleAware;
import com.lamp.electron.core.manage.aware.InsideServiceFactoryAware;
import com.lamp.electron.core.manage.aware.InterfaceAware;
import com.lamp.electron.core.service.InsideServiceFactory;

import lombok.Setter;

/**
 * 要排除OrganizationTypeEnum.INTERFACE_EXAMPLE
 * 
 * @author laohu
 *
 */
@Setter
@SuppressWarnings("unchecked")
public class AbilityManage implements AbilityRelationRegister {

	@SuppressWarnings("rawtypes")
	private AbstractChainAbility invokingAbility;

	private Map<OrganizationTypeEnum/* organization */ , Map<String/* id */, Map<AbilityType, Ability>>> organizationAbility = new ConcurrentHashMap<>();

	private Map<AbilityType, Class<?>> abiltiyClass = new HashMap<>();

	private Map<EffectPoint, List<AbilityType>> effectPointAbiltiyList = new HashMap<>();

	private InterfaceManage interfaceManage;

	private ExampleManage exampleManage;
	
	private InsideServiceFactory serviceFactory;

	private ConfigPerceptionFactory configPerceptionFactory;
	
	private ContainerBeanFactory containerBeanFactory;

	{
		invokingAbility = new InvokingAbility();
		invokingAbility.setAbilityTypeEnum(AbilityType.INVOKING);
		invokingAbility.setOrganizationTypeEnum(OrganizationTypeEnum.INTERFACE);

		for (OrganizationTypeEnum organizationTypeEnum : OrganizationTypeEnum.values()) {
			organizationAbility.put(organizationTypeEnum, new ConcurrentHashMap<>());
		}

		// TODO 以后用其他方式吧，实在没有时间

		List<AbilityType> abilityTypeEnum = new ArrayList<>();
		abilityTypeEnum.add(AbilityType.DATAINJECTION);
		abilityTypeEnum.add(AbilityType.AUTHENTICATION);
		abilityTypeEnum.add(AbilityType.PAREMVERIFCATION);
		abilityTypeEnum.add(AbilityType.TRAFFISSAFETY);
		abilityTypeEnum.add(AbilityType.SECKILL);
		abilityTypeEnum.add(AbilityType.LOADBALANCING);
		abilityTypeEnum.add(AbilityType.PARTITION);
		abilityTypeEnum.add(AbilityType.HOTSTANDBY);
		abilityTypeEnum.add(AbilityType.STATISTICS);
		effectPointAbiltiyList.put(EffectPoint.DATA, abilityTypeEnum);

		abilityTypeEnum = new ArrayList<>();
		effectPointAbiltiyList.put(EffectPoint.POST, abilityTypeEnum);

		abilityTypeEnum = new ArrayList<>();
		abilityTypeEnum.add(AbilityType.ERRERRESULT);
		effectPointAbiltiyList.put(EffectPoint.ERROR, abilityTypeEnum);

		abilityTypeEnum = new ArrayList<>();
		abilityTypeEnum.add(AbilityType.STATISTICS);
		abilityTypeEnum.add(AbilityType.ALARM);
		abilityTypeEnum.add(AbilityType.DATAINJECTION);
		effectPointAbiltiyList.put(EffectPoint.RESULT, abilityTypeEnum);

		// TODO 以后用其他方式吧，实在没有时间

		abiltiyClass.put(AbilityType.STATISTICS, StatisticsAbility.class);
		abiltiyClass.put(AbilityType.REQUESTRECORD, RequestRecordAbility.class);

		abiltiyClass.put(AbilityType.CONDITIONROUTE, ConditionAbility.class);

		abiltiyClass.put(AbilityType.INTERFACERESGISTER, InterfaceRegisterAbiltiy.class);
		abiltiyClass.put(AbilityType.RESOURCESRESGISTER, ResourcesRegisterAbility.class);
		abiltiyClass.put(AbilityType.EXAMPLEINFOREGISTER, ExampleinfoRegisterAbility.class);

		abiltiyClass.put(AbilityType.HOTSTANDBY, HotStandbyAbility.class);
		abiltiyClass.put(AbilityType.PARTITION, PartitionAbility.class);
		abiltiyClass.put(AbilityType.LOADBALANCING, LoadBalancingAbiltiy.class);

		abiltiyClass.put(AbilityType.SECKILL, SeckillAbility.class);
		abiltiyClass.put(AbilityType.TRAFFISSAFETY, TraffisSafetyAbility.class);
		abiltiyClass.put(AbilityType.INTERFACERESGISTER, InterfaceRegisterAbiltiy.class);
		abiltiyClass.put(AbilityType.AUTHENTICATION, AuthenticationAbility.class);
		abiltiyClass.put(AbilityType.PAREMVERIFCATION, ParemVerifcationAbility.class);

		abiltiyClass.put(AbilityType.ERRERRESULT, ErrorResultAbiliby.class);
		abiltiyClass.put(AbilityType.ALARM, AlarmAbility.class);

		abiltiyClass.put(AbilityType.DATAINJECTION, DataInjectionAbility.class);

		AbstractChainAbility<Object> ability = (AbstractChainAbility<Object>) getChainAbility(
				AbilityType.LOADBALANCING, OrganizationTypeEnum.SYSTEM_DEFAULT,
				OrganizationTypeEnum.SYSTEM_DEFAULT.name());
		LoadBalancing loadBalancing = new LoadBalancing();
		loadBalancing.setName(LoadBalancingEnum.RANDOM);
		ability.setAbilityObject(loadBalancing);

		// TODO 测试用 代码
		
	}

	void testAbility() {
		AbilityRelation abilityRelation = new AbilityRelation();
		abilityRelation.setAbilityTypeEnum(AbilityType.AUTHENTICATION);
		abilityRelation.setOrganizationName("test-springmvc");
		abilityRelation.setOrganizationTypeEnum(OrganizationTypeEnum.APPLICATION);
		Authentication authentication = new Authentication();
		authentication.setApplicationName("test-springmvc");
		authentication.setTokenSpot(DataSpot.HEADER);
		authentication.setTokenName("token");
		authentication.setRedirectSpot(DataSpot.REDIRECT);
		authentication.setRedirectData("www.baidu.com");
		authentication.setUserKey("id");
		Set<String> acrossPathList = new HashSet<>();
		acrossPathList.add("/electron/example/example/queryExample");
		acrossPathList.add("/electron/example/auth/userAuth");
		authentication.setAcrossPathList(acrossPathList);
		abilityRelation.setAbility(authentication);
		getChainAbility(abilityRelation).addAbilityObject(abilityRelation);
	}

	public AbilityManage(ExampleManage exampleManage, InterfaceManage interfaceManage,
			ConfigPerceptionFactory configPerceptionFactory) {
		this.exampleManage = exampleManage;
		this.interfaceManage = interfaceManage;
		this.configPerceptionFactory = configPerceptionFactory;
		this.createOverallSituation();
	}
	
	public void init() {
		// TODO 测试用 代码
		//testAbility();
	}

	private void createOverallSituation() {
		Map<String/* id */, Map<AbilityType, Ability>> overallSituation = organizationAbility
				.get(OrganizationTypeEnum.APPLICATION);
		Map<AbilityType, Ability> overallSituationMap = new HashMap<>();
		overallSituation.put("organization", overallSituationMap);

		overallSituationMap.put(AbilityType.CONDITIONROUTE, new ConditionAbility());

		overallSituationMap.put(AbilityType.INTERFACERESGISTER, new InterfaceRegisterAbiltiy());
		overallSituationMap.put(AbilityType.EXAMPLEINFOREGISTER, new ExampleinfoRegisterAbility());
		overallSituationMap.put(AbilityType.RESOURCESRESGISTER, new ResourcesRegisterAbility());
		overallSituationMap.put(AbilityType.CONFIG, new ConfigAbility());
		for (Ability ability : overallSituationMap.values()) {
			if (ability instanceof InterfaceAware) {
				((InterfaceAware) ability).setInterfaceManage(interfaceManage);
			}
			if (ability instanceof ExampleAware) {
				((ExampleAware) ability).setExampleManage(exampleManage);
			}
			if (ability instanceof ConfigPerceptionAware) {
				((ConfigPerceptionAware) ability).setConfigPerceptionFactory(configPerceptionFactory);
			}
		}

	}

	@Override
	public int register(AbilityRelation abilityRelation) {
		abilityRelation.setAbility(serializeAbility(abilityRelation));
		getChainAbility(abilityRelation).addAbilityObject(abilityRelation);
		return 0;
	}

	@Override
	public int unRegister(AbilityRelation abilityRelation) {
		getChainAbility(abilityRelation).remoteAbilityObject(abilityRelation);
		return 0;
	}

	public ExecuteAbility getErrorExecuteAbility(LongRangeWrapper longRangeWrapper) {
		ExecuteAbility executeAbility = getAbility(null, EffectPoint.RESULT, longRangeWrapper);
		return getAbility(executeAbility, EffectPoint.ERROR, longRangeWrapper);
	}

	public ExecuteAbility getPostExecuteAbility(LongRangeWrapper longRangeWrapper) {
		ExecuteAbility executeAbility = getAbility(null, EffectPoint.RESULT, longRangeWrapper);
		return getAbility(executeAbility, EffectPoint.POST, longRangeWrapper);
	}

	public ExecuteAbility getExecuteAbility(LongRangeWrapper longRangeWrapper) {
		ExecuteAbility executeAbility = getAbility(null, EffectPoint.RESULT, longRangeWrapper);
		executeAbility = getAbility(executeAbility, EffectPoint.POST, longRangeWrapper);
		return getAbility(new ExecuteAbility(invokingAbility, executeAbility), EffectPoint.DATA, longRangeWrapper);
	}

	/**
	 * DATA->RUN->POST->RESULT</br>
	 * ERROR->RESULT</br>
	 * POST->RESULT</br>
	 * 
	 * @param executeAbility
	 * @param effectPoint
	 * @param longRangeWrapper
	 * @return
	 */
	private ExecuteAbility getAbility(ExecuteAbility executeAbility, EffectPoint effectPoint,
			LongRangeWrapper longRangeWrapper) {
		List<AbilityType> abilityTypeEnumList = effectPointAbiltiyList.get(effectPoint);
		for (AbilityType abilityTypeEnum : abilityTypeEnumList) {
			executeAbility = getExecuteAbility(abilityTypeEnum, executeAbility, longRangeWrapper, effectPoint);
		}
		return executeAbility;
	}

	public ExecuteAbility getExecuteAbility(AbilityType abilityTypeEnum, ExecuteAbility executeAbility,
			LongRangeWrapper longRangeWrapper, EffectPoint effectPoint) {
		List<AbstractChainAbility<Object>> list = new ArrayList<>();
		for (OrganizationTypeEnum organizationTypeEnum : abilityTypeEnum.getAbiltityBindRelation()) {
			AbstractChainAbility<Object> ability = (AbstractChainAbility<Object>) getChainAbility(abilityTypeEnum,
					organizationTypeEnum, abilityId(longRangeWrapper, organizationTypeEnum));
			if (effectPoint.equals(EffectPoint.ERROR) && ability instanceof ErrorAbility) {
				ability = new ErrorAbilityWrapper((ErrorAbility) ability);
			} else if (effectPoint.equals(EffectPoint.POST) && ability instanceof PostAbility) {
				ability = new PostAbilityWrapper((PostAbility) ability);
			} else if (effectPoint.equals(EffectPoint.RESULT) && ability instanceof ResultAbility) {
				ability = new ResultAbilityWrapper((ResultAbility) ability);
			}
			list.add(ability);
		}
		return new ExecuteAbility(list, executeAbility, null);
	}

	public <T> T getOverallSituationAbility(AbilityType abilityTypeEnum) {
		return (T) getAbilityMap(abilityTypeEnum, null, null).get(abilityTypeEnum);
	}

	private Ability getChainAbility(AbilityRelation abilityRelation) {
		return getChainAbility(abilityRelation.getAbilityTypeEnum(), abilityRelation.getOrganizationTypeEnum(),
				abilityRelation.getOrganizationName());
	}

	private Ability getChainAbility(AbilityType abilityTypeEnum, OrganizationTypeEnum organizationTypeEnum,
			String id) {
		Map<AbilityType, Ability> abilityTypeEnumMap = getAbilityMap(abilityTypeEnum, organizationTypeEnum, id);
		Ability chainAbility = abilityTypeEnumMap.get(abilityTypeEnum);
		if (Objects.isNull(chainAbility)) {
			chainAbility = abilityTypeEnumMap.computeIfAbsent(abilityTypeEnum,
					new Function<AbilityType, Ability>() {

						@Override
						public Ability apply(AbilityType abilityTypeEnum) {
							try {
								Class<?> clazz = abiltiyClass.get(abilityTypeEnum);
								AbstractAbility<Object> ability = (AbstractAbility<Object>) clazz.newInstance();
								ability.setAbilityTypeEnum(abilityTypeEnum);
								ability.setOrganizationTypeEnum(organizationTypeEnum);
								if( ability instanceof InsideServiceFactoryAware) {
									((InsideServiceFactoryAware)ability).setInsideServiceFactory(serviceFactory);
								}
								containerBeanFactory.rely(ability);
								return ability;
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}
					});
		}
		return chainAbility;
	}

	private Map<AbilityType, Ability> getAbilityMap(AbilityType abilityTypeEnum,
			OrganizationTypeEnum organizationTypeEnum, String id) {
		if (abilityTypeEnum.isOverallSituation()) {
			organizationTypeEnum = OrganizationTypeEnum.APPLICATION;
			id = "organization";
		}
		Map<String, Map<AbilityType, Ability>> organizationAbilityMap = organizationAbility
				.get(organizationTypeEnum);
		Map<AbilityType, Ability> abilityTypeEnumMap = organizationAbilityMap.get(id);
		if (Objects.isNull(abilityTypeEnumMap)) {
			abilityTypeEnumMap = organizationAbilityMap.computeIfAbsent(id, k -> new ConcurrentHashMap<>());
		}
		return abilityTypeEnumMap;
	}

	/**
	 * 序列化对象
	 * 
	 * @param abilityRelation
	 * @return
	 */
	private Object serializeAbility(AbilityRelation abilityRelation) {
		JSONObject object = (JSONObject) abilityRelation.getAbility();
		return object.toJavaObject(abilityRelation.getAbilityTypeEnum().getAbiltiyObject());
	}

	private String abilityId(LongRangeWrapper longRangeWrapper, OrganizationTypeEnum organizationTypeEnum) {
		if (OrganizationTypeEnum.INTERFACE == organizationTypeEnum) {
			return longRangeWrapper.getPath();
		} else if (OrganizationTypeEnum.APPLICATION == organizationTypeEnum) {
			return longRangeWrapper.getApplicationName();
		} else if (OrganizationTypeEnum.SYSTEM_DEFAULT == organizationTypeEnum) {
			return OrganizationTypeEnum.SYSTEM_DEFAULT.name();
		}

		return null;
	}
}
