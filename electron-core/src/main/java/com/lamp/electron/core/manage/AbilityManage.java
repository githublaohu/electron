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

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.lamp.electron.base.common.ability.Authentication;
import com.lamp.electron.base.common.ability.LoadBalancing;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
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
import com.lamp.electron.core.ability.discern.ConditionRouterAbility;
import com.lamp.electron.core.ability.discern.ExampleinfoRegisterAbility;
import com.lamp.electron.core.ability.discern.InterfaceRegisterAbility;
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
import com.lamp.electron.core.ability.route.LoadBalancingAbility;
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

	private Map<OrganizationTypeEnum/* organization */ , Map<String/* id */, Map<AbilityTypeEnum, Ability>>> organizationAbility = new ConcurrentHashMap<>();

	private Map<AbilityTypeEnum, Class<?>> abilityClass = new HashMap<>();

	private Map<EffectPoint, List<AbilityTypeEnum>> effectPointAbilityList = new HashMap<>();

	private InterfaceManage interfaceManage;

	private ExampleManage exampleManage;
	
	private InsideServiceFactory serviceFactory;

	@Resource
	private ConfigPerceptionFactory configPerceptionFactory;
	
	@Resource
	private ContainerBeanFactory containerBeanFactory;

	private void initAbility(){
		invokingAbility = new InvokingAbility();
		invokingAbility.setAbilityTypeEnum(AbilityTypeEnum.INVOKING);
		invokingAbility.setOrganizationTypeEnum(OrganizationTypeEnum.INTERFACE);

		for (OrganizationTypeEnum organizationTypeEnum : OrganizationTypeEnum.values()) {
			organizationAbility.put(organizationTypeEnum, new ConcurrentHashMap<>());
		}

		// TODO 以后用其他方式吧，实在没有时间

		List<AbilityTypeEnum> abilityTypeEnum = new ArrayList<>();
		abilityTypeEnum.add(AbilityTypeEnum.DATAINJECTION);
		abilityTypeEnum.add(AbilityTypeEnum.AUTHENTICATION);
		abilityTypeEnum.add(AbilityTypeEnum.PAREMVERIFCATION);
		abilityTypeEnum.add(AbilityTypeEnum.TRAFFISSAFETY);
		abilityTypeEnum.add(AbilityTypeEnum.SECKILL);
		abilityTypeEnum.add(AbilityTypeEnum.LOADBALANCING);
		abilityTypeEnum.add(AbilityTypeEnum.PARTITION);
		abilityTypeEnum.add(AbilityTypeEnum.HOTSTANDBY);
		abilityTypeEnum.add(AbilityTypeEnum.STATISTICS);
		effectPointAbilityList.put(EffectPoint.DATA, abilityTypeEnum);

		abilityTypeEnum = new ArrayList<>();
		effectPointAbilityList.put(EffectPoint.POST, abilityTypeEnum);

		abilityTypeEnum = new ArrayList<>();
		abilityTypeEnum.add(AbilityTypeEnum.ERRERRESULT);
		effectPointAbilityList.put(EffectPoint.ERROR, abilityTypeEnum);

		abilityTypeEnum = new ArrayList<>();
		abilityTypeEnum.add(AbilityTypeEnum.STATISTICS);
		abilityTypeEnum.add(AbilityTypeEnum.ALARM);
		abilityTypeEnum.add(AbilityTypeEnum.DATAINJECTION);
		effectPointAbilityList.put(EffectPoint.RESULT, abilityTypeEnum);

		// TODO 以后用其他方式吧，实在没有时间

		abilityClass.put(AbilityTypeEnum.STATISTICS, StatisticsAbility.class);
		abilityClass.put(AbilityTypeEnum.REQUESTRECORD, RequestRecordAbility.class);

		abilityClass.put(AbilityTypeEnum.CONDITIONROUTE, ConditionRouterAbility.class);

		abilityClass.put(AbilityTypeEnum.INTERFACERESGISTER, InterfaceRegisterAbility.class);
		abilityClass.put(AbilityTypeEnum.RESOURCESRESGISTER, ResourcesRegisterAbility.class);
		abilityClass.put(AbilityTypeEnum.EXAMPLEINFOREGISTER, ExampleinfoRegisterAbility.class);

		abilityClass.put(AbilityTypeEnum.HOTSTANDBY, HotStandbyAbility.class);
		abilityClass.put(AbilityTypeEnum.PARTITION, PartitionAbility.class);
		abilityClass.put(AbilityTypeEnum.LOADBALANCING, LoadBalancingAbility.class);

		abilityClass.put(AbilityTypeEnum.SECKILL, SeckillAbility.class);
		abilityClass.put(AbilityTypeEnum.TRAFFISSAFETY, TraffisSafetyAbility.class);
		abilityClass.put(AbilityTypeEnum.INTERFACERESGISTER, InterfaceRegisterAbility.class);
		abilityClass.put(AbilityTypeEnum.AUTHENTICATION, AuthenticationAbility.class);
		abilityClass.put(AbilityTypeEnum.PAREMVERIFCATION, ParemVerifcationAbility.class);

		abilityClass.put(AbilityTypeEnum.ERRERRESULT, ErrorResultAbiliby.class);
		abilityClass.put(AbilityTypeEnum.ALARM, AlarmAbility.class);

		abilityClass.put(AbilityTypeEnum.DATAINJECTION, DataInjectionAbility.class);

		AbstractChainAbility<Object> ability = (AbstractChainAbility<Object>) getChainAbility(
				AbilityTypeEnum.LOADBALANCING, OrganizationTypeEnum.SYSTEM_DEFAULT,
				OrganizationTypeEnum.SYSTEM_DEFAULT.name());
		LoadBalancing loadBalancing = new LoadBalancing();
		loadBalancing.setName(LoadBalancingEnum.RANDOM);
		ability.setAbilityObject(loadBalancing);

		// TODO 测试用 代码
		
	}

	void testAbility() {
		AbilityRelation abilityRelation = new AbilityRelation();
		abilityRelation.setAbilityTypeEnum(AbilityTypeEnum.AUTHENTICATION);
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
	}
	
	public void init() {
		this.initAbility();
		this.createOverallSituation();
		// TODO 测试用 代码
		//testAbility();
	}

	private void createOverallSituation() {
		Map<String/* id */, Map<AbilityTypeEnum, Ability>> overallSituation = organizationAbility
				.get(OrganizationTypeEnum.APPLICATION);
		Map<AbilityTypeEnum, Ability> overallSituationMap = new HashMap<>();
		overallSituation.put("organization", overallSituationMap);

		overallSituationMap.put(AbilityTypeEnum.CONDITIONROUTE, new ConditionRouterAbility());

		overallSituationMap.put(AbilityTypeEnum.INTERFACERESGISTER, new InterfaceRegisterAbility());
		overallSituationMap.put(AbilityTypeEnum.EXAMPLEINFOREGISTER, new ExampleinfoRegisterAbility());
		overallSituationMap.put(AbilityTypeEnum.RESOURCESRESGISTER, new ResourcesRegisterAbility());
		overallSituationMap.put(AbilityTypeEnum.CONFIG, new ConfigAbility());
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
		List<AbilityTypeEnum> abilityTypeEnumList = effectPointAbilityList.get(effectPoint);
		for (AbilityTypeEnum abilityTypeEnum : abilityTypeEnumList) {
			executeAbility = getExecuteAbility(abilityTypeEnum, executeAbility, longRangeWrapper, effectPoint);
		}
		return executeAbility;
	}

	public ExecuteAbility getExecuteAbility(AbilityTypeEnum abilityTypeEnum, ExecuteAbility executeAbility,
											LongRangeWrapper longRangeWrapper, EffectPoint effectPoint) {
		List<AbstractChainAbility<Object>> list = new ArrayList<>();
		for (OrganizationTypeEnum organizationTypeEnum : abilityTypeEnum.getAbilityBindRelation()) {
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

	public <T> T getOverallSituationAbility(AbilityTypeEnum abilityTypeEnum) {
		return (T) getAbilityMap(abilityTypeEnum, null, null).get(abilityTypeEnum);
	}

	private Ability getChainAbility(AbilityRelation abilityRelation) {
		return getChainAbility(abilityRelation.getAbilityTypeEnum(), abilityRelation.getOrganizationTypeEnum(),
				abilityRelation.getOrganizationName());
	}

	private Ability getChainAbility(AbilityTypeEnum abilityTypeEnum, OrganizationTypeEnum organizationTypeEnum,
									String id) {
		Map<AbilityTypeEnum, Ability> abilityTypeEnumMap = getAbilityMap(abilityTypeEnum, organizationTypeEnum, id);
		Ability chainAbility = abilityTypeEnumMap.get(abilityTypeEnum);
		if (Objects.isNull(chainAbility)) {
			chainAbility = abilityTypeEnumMap.computeIfAbsent(abilityTypeEnum,
					new Function<AbilityTypeEnum, Ability>() {

						@Override
						public Ability apply(AbilityTypeEnum abilityTypeEnum) {
							try {
								Class<?> clazz = abilityClass.get(abilityTypeEnum);
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

	private Map<AbilityTypeEnum, Ability> getAbilityMap(AbilityTypeEnum abilityTypeEnum,
														OrganizationTypeEnum organizationTypeEnum, String id) {
		if (abilityTypeEnum.isOverallSituation()) {
			organizationTypeEnum = OrganizationTypeEnum.APPLICATION;
			id = "organization";
		}
		Map<String, Map<AbilityTypeEnum, Ability>> organizationAbilityMap = organizationAbility
				.get(organizationTypeEnum);
		Map<AbilityTypeEnum, Ability> abilityTypeEnumMap = organizationAbilityMap.get(id);
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
		return object.toJavaObject(abilityRelation.getAbilityTypeEnum().getAbilityObject());
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
