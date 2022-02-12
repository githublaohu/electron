package com.lamp.electron.core.ability;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.core.manage.AbilityManage;

@RunWith(MockitoJUnitRunner.class)
public class AbilityInvokerTest {

	AbilityManage abilityManage = new AbilityManage(null, null, null);

	@Test
	public void init() {
		LongRangeWrapper longRangeWrapper = new LongRangeWrapper("/electron", "electron", new AtomicLong());

		AbilityInvoker abilityInvoker = new AbilityInvoker(abilityManage.getExecuteAbility(longRangeWrapper),
				abilityManage.getPostExecuteAbility(longRangeWrapper),
				abilityManage.getErrorExecuteAbility(longRangeWrapper));
		ElectronRequest electronRequest = Mockito.mock(ElectronRequest.class);
		abilityInvoker.run(electronRequest, null, null);
	}

}
