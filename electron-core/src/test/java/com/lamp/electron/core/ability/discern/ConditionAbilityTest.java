package com.lamp.electron.core.ability.discern;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.lamp.electron.base.common.ability.ConditionRouter;
import com.lamp.electron.base.common.ability.ConditionRouter.Condition;
import com.lamp.electron.base.common.ability.ConditionRouter.Rewrite;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.register.data.AbilityRelation;

@RunWith(MockitoJUnitRunner.class)
public class ConditionAbilityTest {

	@Spy
	private ConditionAbility conditionAbility = new ConditionAbility();

	@Mock
	private ElectronRequest electronRequest;

	AbilityRelation abilityRelation = new AbilityRelation();

	ConditionRouter conditionRouter = new ConditionRouter();

	List<Condition> list = new ArrayList<ConditionRouter.Condition>();

	@Before
	public void before() {

		abilityRelation.setAbilityTypeEnum(AbilityTypeEnum.CONDITIONROUTE);
		abilityRelation.setOrganizationTypeEnum(OrganizationTypeEnum.APPLICATION);
		abilityRelation.setOrganizationName("electron");

		Condition condition = new Condition();
		condition.setDataSpot(DataSpot.URL);
		condition.setKey("/electron");
		condition.setRewrite(Rewrite.UNCHANGED);
		list.add(condition);
		conditionRouter.setConditions(list);
		abilityRelation.setAbility(conditionRouter);

	}

	@Test
	public void addAbilityObjectTestNull() {
		AbilityRelation abilityRelation = new AbilityRelation();
		abilityRelation.setAbility(conditionRouter);
		Assert.assertNull(abilityRelation.getAbilityTypeEnum());
		Assert.assertNull(abilityRelation.getOrganizationTypeEnum());

		abilityRelation.setOrganizationName("/electron");
		conditionAbility.addAbilityObject(abilityRelation);
		Assert.assertNull(conditionAbility.getAbilityTypeEnum());
		Assert.assertNull(conditionAbility.getOrganizationTypeEnum());

		conditionAbility.addAbilityObject(this.abilityRelation);
		Assert.assertNotNull(conditionAbility.getAbilityTypeEnum());
		Assert.assertNotNull(conditionAbility.getOrganizationTypeEnum());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void doAddAbilityObjectTest() throws IllegalArgumentException, IllegalAccessException {
		Field conditionAbilityField = FieldUtils.getField(conditionAbility.getClass(), "abilityDataMap", true);
		Map<String, ConditionRouter> abilityDataMap = (Map<String, ConditionRouter>) conditionAbilityField
				.get(conditionAbility);
		Assert.assertTrue(abilityDataMap.isEmpty());
		conditionAbility.addAbilityObject(this.abilityRelation);
		Assert.assertFalse(abilityDataMap.isEmpty());

		ConditionRouter conditionRouter = abilityDataMap.get(abilityRelation.getOrganizationName());
		Assert.assertEquals(conditionRouter, abilityRelation.getAbility());

		conditionAbility.remoteAbilityObject(abilityRelation);
		Assert.assertTrue(abilityDataMap.isEmpty());
	}

	@Test
	public void urlTest() {
		Mockito.when(electronRequest.path()).thenReturn("/a/electron/before");
		String name = conditionAbility.discern(electronRequest);
		Assert.assertNull(name);

		conditionAbility.addAbilityObject(this.abilityRelation);
		name = conditionAbility.discern(electronRequest);
		Assert.assertNull(name);

		Mockito.when(electronRequest.path()).thenReturn("/electron/before");
		name = conditionAbility.discern(electronRequest);
		Assert.assertEquals(name, abilityRelation.getOrganizationName());
	}

	@Test
	public void rewriteValusTest() {

		conditionAbility.addAbilityObject(abilityRelation);
		Mockito.when(electronRequest.path()).thenReturn("/electron/before");
		
		Condition condition = list.get(0);
		condition.setRewrite(Rewrite.REWRITE_KEY);
		condition.setRewriteValue("");
		conditionAbility.discern(electronRequest);
		condition.setRewriteValue("");
		conditionAbility.discern(electronRequest);
		condition.setRewriteValue("/");
		conditionAbility.discern(electronRequest);
		Mockito.verify(electronRequest,Mockito.times(0)).path(Mockito.anyString());
		
		Mockito.doAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				Assert.assertEquals( invocation.getArgument(0), "/before");
				return invocation.getArgument(0);
			}
		}).when(electronRequest).path(Mockito.any());
		condition.setRewrite(Rewrite.DETELE_KEY);
		conditionAbility.discern(electronRequest);
		
		Mockito.doAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				Assert.assertEquals(invocation.getArgument(0), "/electron/core/before");
				return invocation.getArgument(0);
			}
		}).when(electronRequest).path(Mockito.any());
		condition.setRewrite(Rewrite.REWRITE_KEY);
		condition.setRewriteValue("/electron/core");
		conditionAbility.discern(electronRequest);
	}

	@Test
	public void dataTest() {
		conditionAbility.addAbilityObject(abilityRelation);
		Mockito.when(electronRequest.path()).thenReturn("/a/electron/before");
		Condition condition = new Condition();
		condition.setDataSpot(DataSpot.QUERY);
		condition.setKey("electron");
		condition.setRewrite(Rewrite.UNCHANGED);
		list.add(condition);
		
		Mockito.when(electronRequest.data(Mockito.any(),Mockito.anyString())).thenReturn(abilityRelation.getOrganizationName());
		String name = conditionAbility.discern(electronRequest);
		Assert.assertEquals(name,abilityRelation.getOrganizationName());
		
		Mockito.when(electronRequest.data(Mockito.any(),Mockito.anyString())).thenReturn("");
		name = conditionAbility.discern(electronRequest);
		Assert.assertNull(name);
		
		condition.setValue("core");
		
		Mockito.when(electronRequest.data(Mockito.any(),Mockito.anyString())).thenReturn("core");
		name = conditionAbility.discern(electronRequest);
		Assert.assertEquals(name,abilityRelation.getOrganizationName());
		
		
	}
}
