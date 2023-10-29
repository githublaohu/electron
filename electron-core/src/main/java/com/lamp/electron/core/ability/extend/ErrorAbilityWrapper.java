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
package com.lamp.electron.core.ability.extend;

import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;

/**
 * @author jellly
 */
public class ErrorAbilityWrapper extends AbstractChainAbilityWrapper{

	
	private ErrorAbility errorAbility;
	
	public ErrorAbilityWrapper(ErrorAbility errorAbility) {
		super(errorAbility);
		this.errorAbility = errorAbility;
	}
	
	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		return errorAbility.error(electronRequest, electronResponse, invoker);
	}

	@Override
	public String toString() {
		return "ErrorAbilityWrapper [errorAbility=" + errorAbility + "]";
	}
	
	

}
