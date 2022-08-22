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
package com.lamp.electron.core.ability;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.exception.ExceptionType;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.core.ability.discern.ConditionRouterAbility;
import com.lamp.electron.core.manage.AbilityManage;
import com.lamp.electron.core.manage.InstanceManage;
import com.lamp.electron.core.manage.InterfaceManage;
import com.lamp.electron.rpc.api.AbstractElectronBehavior;

import io.netty.handler.codec.http.HttpResponseStatus;

public class AbilityInvokerManage {

	private Map<String, AbilityInvoker> apiExecuteAbstractAbility = new ConcurrentHashMap<>();

	private AbilityManage abilityManage;

	private InterfaceManage interfaceManage;

	private InstanceManage instanceManage;

	private ConditionRouterAbility conditionAbility;

	public AbilityInvokerManage(AbilityManage abilityManage, InterfaceManage interfaceManage,
			InstanceManage instanceManage) {
		this.abilityManage = abilityManage;
		this.interfaceManage = interfaceManage;
		this.instanceManage = instanceManage;
		this.conditionAbility = abilityManage.getOverallSituationAbility(AbilityTypeEnum.CONDITION_ROUTER);
	}

	public AbilityInvoker getAbilityInvoker(ElectronRequest electronRequest) {
		ElectronResponse electronResponse = null;
		LongRangeWrapper longRangeWrapper = null;
		try {
			// 获得接口信息，先全路径匹配
			longRangeWrapper = interfaceManage.getLongRangeWrapper(electronRequest.path());
			// 匹配条件路由
			if (Objects.isNull(longRangeWrapper)) {
				String applicationName = conditionAbility.discern(electronRequest);
				if (Objects.isNull(applicationName)) {
					electronResponse = ExceptionType.REQUEST_RESOURCE_NOT_FIND.wrapper(electronRequest, HttpResponseStatus.NOT_FOUND);
				} else {
					// 获取服务实例
					longRangeWrapper = instanceManage.getInstanceInfo(applicationName);
					if (Objects.isNull(longRangeWrapper)) {
						electronResponse = ExceptionType.REQUEST_GET_NOT_SERVICE.wrapper(electronRequest, applicationName);
					}
				}
			}
			if (Objects.nonNull(longRangeWrapper) && !longRangeWrapper.isExistInstance()) {
				electronResponse = ExceptionType.REQUEST_NOT_INSTANCE.wrapper(electronRequest, longRangeWrapper.getApplicationName());
			}
			if (Objects.nonNull(electronResponse)) {
				// 返回异常
				electronRequest.getAgreementResponse().reply(electronResponse, electronRequest);
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			electronResponse = ExceptionType.UNKNOWN_ERROR.wrapper(electronRequest, "未知异常", e.getMessage());
			electronRequest.getAgreementResponse().reply(electronResponse, electronRequest);
			return null;
		}
		return interfaceAndAbility(electronRequest, longRangeWrapper);
	}

	public AbilityInvoker interfaceAndAbility(ElectronRequest electronRequest, LongRangeWrapper longRangeWrapper) {
		AbilityInvoker ability = apiExecuteAbstractAbility.get(longRangeWrapper.getPath());
		if (Objects.isNull(ability)) {
			ability = apiExecuteAbstractAbility.computeIfAbsent(electronRequest.path(),
					new Function<String, AbilityInvoker>() {
						@Override
						public AbilityInvoker apply(String key) {
							return new AbilityInvoker(abilityManage.getExecuteAbility(longRangeWrapper),
									abilityManage.getPostExecuteAbility(longRangeWrapper),
									abilityManage.getErrorExecuteAbility(longRangeWrapper));
						}
					});

		}
		AbstractElectronBehavior abstractElectronBehavior = (AbstractElectronBehavior)electronRequest;
		abstractElectronBehavior.setLongRangeWrapper(longRangeWrapper);
		abstractElectronBehavior.setNetworkAddressList(longRangeWrapper.getNetworkAddressList());
		return new AbilityInvoker(ability);
	}
}
