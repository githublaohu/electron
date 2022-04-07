package com.lamp.electron.core.manage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lamp.electron.base.common.ability.ConditionRouter;
import com.lamp.electron.base.common.ability.ConditionRouter.Condition;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.register.data.AbilityRelation;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.core.ability.Ability;
import com.lamp.electron.core.ability.ExecuteAbility;

public class AbilityManageTest {

	private AbilityManage abilityManage = new AbilityManage(null, null,null);
	
	@Test
	public void test() {
		AbilityRelation abilityRelation = new AbilityRelation();
		
		ConditionRouter conditionRoute = new ConditionRouter();
		List<Condition> conditionList = new ArrayList<>();
		Condition condition = new Condition();
		condition.setKey("ddd");
		condition.setValue("aaa");
		conditionList.add(condition);
		conditionRoute.setConditions(conditionList);
		abilityRelation.setAbility(conditionRoute);
		String str = JSON.toJSONString(abilityRelation);
		
		AbilityRelation newAbilityRelation =  JSON.parseObject(str, AbilityRelation.class);
		System.out.println(newAbilityRelation);
		JSONObject object = (JSONObject)newAbilityRelation.getAbility();
		ConditionRouter conditionRouter = (ConditionRouter) object.toJavaObject(AbilityTypeEnum.CONDITION_ROUTER.getAbilityObject());
		System.out.println(conditionRouter);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void createOverallSituation() throws Exception {
		abilityManage.init();
		Field organizationAbilityField = abilityManage.getClass().getDeclaredField("organizationAbility");
		organizationAbilityField.setAccessible(true);
		Map<OrganizationTypeEnum/* organization */ , Map<String/* id */, Map<AbilityTypeEnum, Ability>>> organizationAbility = (Map<OrganizationTypeEnum , Map<String, Map<AbilityTypeEnum, Ability>>>)organizationAbilityField.get(abilityManage);
		
		Map<String/* id */, Map<AbilityTypeEnum, Ability>> overallSituation = organizationAbility
				.get(OrganizationTypeEnum.APPLICATION);
		Map<AbilityTypeEnum, Ability> overallSituationMap = overallSituation.get("organization");
	}
	
	@Test
	public void getExecuteAbility() {
		LongRangeWrapper longRangeWrapper = new LongRangeWrapper("/long/range/wrapper","ability",null);
		
		ExecuteAbility executeAbility = abilityManage.getExecuteAbility(longRangeWrapper);
		
		ExecuteAbility postExecuteAbility = abilityManage.getPostExecuteAbility(longRangeWrapper);
		
		ExecuteAbility errorExecuteAbility = abilityManage.getErrorExecuteAbility(longRangeWrapper);
	}
}
