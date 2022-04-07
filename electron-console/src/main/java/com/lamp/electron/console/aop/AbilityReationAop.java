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
package com.lamp.electron.console.aop;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.register.RegisterServerFocusCall;
import com.lamp.electron.base.common.register.data.AbilityInfo;
import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.console.config.RegisterObject;
import com.lamp.electron.console.service.ability.AbilityInfoService;
import com.lamp.electron.console.service.ability.AbilityRelationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AbilityReationAop {

	@Autowired
	private AbilityRelationService abilityRelationService;

	@Autowired
	private AbilityInfoService abilityInfoService;
	
	@Autowired
	private RegisterObject registerObject;
	
	private Map<AbilityTypeEnum,String> abilityTypeByChildDataName = new ConcurrentHashMap<>();
	
	@AfterReturning(returning = "returning", value = "execution(* com.lamp.electron.console.service.ability.AbilityInfoService.insertAbilityInfo(..)) || "
			+ "execution(* com.lamp.electron.console.service.ability.AbilityInfoService.updateAbilityInfoStatus(..)) ||"
			+ "execution(* com.lamp.electron.console.service.ability.AbilityRelationService.bindAbilityRelation(..)) ||"
			+ "execution(* com.lamp.electron.console.service.ability.AbilityRelationService.unbindAbilityRelation(..))")
	public void afterReturning(JoinPoint joinPoint, Integer returning) {
		if (returning <= 0) {
			return;
		}
		try {
			log.info("join point is {}", joinPoint);
			Object object = joinPoint.getArgs()[0];
			if (object instanceof AbilityInfo) {
				AbilityInfo abilityInfo = (AbilityInfo) object;
				if (Objects.isNull(abilityInfo.getAiParentId()) || abilityInfo.getAiParentId() == 0) {
					return;
				}
				this.sendAbilityInfo(abilityInfo);
			} else {
				AbilityRelation abilityRelation = (AbilityRelation) object;
				abilityRelation = abilityRelationService.queryAbilityRelationById(abilityRelation);
				if (Objects.isNull(abilityRelation.getArRelationStatus()) || abilityRelation.getArRelationStatus() == 1) {
					this.bindAbilityRelation(abilityRelation);
				} else {
					this.unBindAbilityRelation(abilityRelation);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void sendAbilityInfo(AbilityInfo abilityInfo) {
		List<AbilityInfo> abilityInfoList = abilityInfoService.queryAbilityInfoByParentId(abilityInfo);
		AbilityInfo parentAbilityInfo = new AbilityInfo();
		parentAbilityInfo.setAiId(abilityInfo.getAiParentId());
		parentAbilityInfo = abilityInfoService.queryAbilityInfoById(parentAbilityInfo);
		AbilityRelation abilityRelation = new AbilityRelation();
		abilityRelation.setAiId(parentAbilityInfo.getAiId());
		List<AbilityRelation> abilityRelationList = abilityRelationService
				.queryAbilityRelationListByAiId(abilityRelation);
		if (Objects.isNull(abilityRelationList) || abilityRelationList.isEmpty()) {
			return;
		}
		Object object = createAbilityObject(abilityInfoList, parentAbilityInfo);
		for (AbilityRelation ar : abilityRelationList) {
			ar.setAbility(object);
		}
		RegisterServerFocusCall registerServerFocusCall = registerObject.getRegisterServerFocusCall(abilityInfo);
		registerServerFocusCall.createAbilityRelationRegister().batchRegister(abilityRelationList);
	}

	private void bindAbilityRelation(AbilityRelation abilityRelation) {
		AbilityInfo abilityInfo = new AbilityInfo();
		abilityInfo.setAiId(abilityRelation.getAiId());
		abilityInfo.setAiParentId(abilityRelation.getAiId());
		List<AbilityInfo> abilityInfoList = abilityInfoService.queryAbilityInfoByParentId(abilityInfo);
		AbilityInfo parentAbilityInfo = abilityInfoService.queryAbilityInfoById(abilityInfo);
		Object object = createAbilityObject(abilityInfoList, parentAbilityInfo);
		abilityRelation.setAbility(object);
		RegisterServerFocusCall registerServerFocusCall = registerObject.getRegisterServerFocusCall(abilityRelation);
		registerServerFocusCall.createAbilityRelationRegister().register(abilityRelation);
	}
	
	private void unBindAbilityRelation(AbilityRelation abilityRelation) {
		RegisterServerFocusCall registerServerFocusCall = registerObject.getRegisterServerFocusCall(abilityRelation);
		registerServerFocusCall.createAbilityRelationRegister().deregister(abilityRelation);
	}

	private Object createAbilityObject(List<AbilityInfo> abilityInfoList, AbilityInfo parentAbilityInfo) {
		String data = parentAbilityInfo.getAiData();
		JSONObject jsonObject = StringUtils.isEmpty(data) ? new JSONObject() : JSON.parseObject(data);

		
		if ( (Objects.isNull(abilityInfoList) && abilityInfoList.isEmpty())) {
			String chilDataName = abilityTypeByChildDataName.get(parentAbilityInfo.getAiAbilityType());
			if(Objects.isNull(chilDataName)) {
				chilDataName = parentAbilityInfo.getAiAbilityType().getChildDataName();
				for(Field field : parentAbilityInfo.getAiAbilityType().getAbilityObject().getDeclaredFields()) {
					if(List.class.equals(field.getDeclaringClass())){
						chilDataName = field.getName();
					}
				}
				abilityTypeByChildDataName.put(parentAbilityInfo.getAiAbilityType(), chilDataName);
			}
			if(StringUtils.isNoneBlank(chilDataName)) {
				return jsonObject;
			}
			JSONArray jsonArray = new JSONArray();
			for (AbilityInfo abilityInfo : abilityInfoList) {
				jsonArray.add(JSON.parseObject(abilityInfo.getAiData()));
			}
			jsonObject.put(chilDataName, jsonArray);
		}
		return jsonObject;
	}

}
