package com.lamp.electron.core.ability;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.ConfigPerceptionFactory;
import com.lamp.electron.core.ability.discern.ConditionAbility;
import com.lamp.electron.core.manage.AbilityManage;
import com.lamp.electron.core.manage.ExampleManage;
import com.lamp.electron.core.manage.InterfaceManage;
import com.lamp.electron.rpc.ElectronClientFactory;


@RunWith(MockitoJUnitRunner.class)
public class AbilityInvokerManageTest {

	@Mock
	ElectronClientFactory electronClientFactory;


	@Mock
	ConditionAbility conditionAbility;
	
	ConfigPerceptionFactory perceptionFactory = new ConfigPerceptionFactory();

	InterfaceManage interfaceManage = new InterfaceManage(electronClientFactory);

	ExampleManage exampleManage = new ExampleManage(electronClientFactory);

	@Mock
	AbilityManage abilityManage;
	
	AbilityInvokerManage abilityInvokerManage;
	
	
	@Before
	public void initTest() throws IllegalArgumentException, IllegalAccessException {
		Mockito.when(abilityManage.getOverallSituationAbility(Mockito.any())).thenReturn(conditionAbility);
		Field conditionAbilityField = FieldUtils.getField(AbilityInvokerManage.class, "conditionAbility",true);
		
		abilityInvokerManage = new AbilityInvokerManage(abilityManage, interfaceManage, exampleManage);

		Assert.assertEquals(conditionAbility, conditionAbilityField.get(abilityInvokerManage));
		
		
	}

	@Test
	public void getAbilityInvokerTestNull() throws IllegalArgumentException, IllegalAccessException {
		Invoker invoker = Mockito.mock(Invoker.class);
		Mockito.when(electronClientFactory.crateRpcClient(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(invoker);
		Mockito.when(electronClientFactory.crateRpcClient(Mockito.any())).thenReturn(invoker);
		
		ElectronRequest electronRequest = Mockito.mock(ElectronRequest.class);
		Mockito.when(electronRequest.path()).thenReturn("/electron/before");
		
		abilityInvokerManage.getAbilityInvoker(electronRequest);
		
		Mockito.when(conditionAbility.discern(Mockito.any())).thenReturn("electron");
		abilityInvokerManage.getAbilityInvoker(electronRequest);

	}

}
