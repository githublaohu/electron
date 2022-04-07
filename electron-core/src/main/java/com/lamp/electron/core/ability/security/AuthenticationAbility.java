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
package com.lamp.electron.core.ability.security;

import java.util.Objects;

import javax.annotation.Resource;

import com.lamp.electron.base.common.ability.Authentication;
import com.lamp.electron.base.common.annotation.AbilityAction;
import com.lamp.electron.base.common.constant.ElectronConstant;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;
import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.exception.ExceptionType;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.service.authentication.AuthResponseEnum;
import com.lamp.electron.base.common.service.authentication.AuthenticationRequestData;
import com.lamp.electron.base.common.service.authentication.AuthenticationResponseData;
import com.lamp.electron.core.ability.AbstractChainAbility;
import com.lamp.electron.core.manage.aware.InsideServiceFactoryAware;
import com.lamp.electron.core.service.InsideServiceFactory;
import com.lamp.electron.core.service.InsideServiceFactory.ServiceAgreementResponse;
import com.lamp.electron.core.service.asyn.AuthenticationAsynService;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证能力
 * @author jellly
 */
@Slf4j
@AbilityAction(abilityType = AbilityTypeEnum.AUTHENTICATION)
public class AuthenticationAbility extends AbstractChainAbility<Authentication> implements InsideServiceFactoryAware {

	@Resource
	private InsideServiceFactory serviceFactory;

	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {

		Authentication authentication = getAbilityData();

		// 1. 判断api,不用做认证
		if (authentication.getAcrossPathList().contains(electronRequest.path())) {
			if (log.isDebugEnabled()) {
				log.info(" path {} 不需要认证", electronRequest.path());
			}
			return null;
		}
		// 2. 获得token
		String token = electronRequest.data(authentication.getTokenSpot(), authentication.getTokenName());

		if (Objects.isNull(token)) {
			// 进行处理异常处理
			return createElectronResponse(electronRequest, ExceptionType.SECURITY_AUTH_TOKEN_NOT_EXIST);
		}
		AuthenticationRequestData authRequestData = new AuthenticationRequestData();
		authRequestData.setToken(token);
		
		AuthenticationAsynService authenticationAsynService = serviceFactory.getService(authentication.getApplicationName(), AuthenticationAsynService.class, null);
		
		authenticationAsynService.userAuth(new ServiceAgreementResponse<AuthenticationResponseData>() {

			@Override
			public void serviceReturn(AuthenticationResponseData data, ElectronResponse electronResponse) {

				if (Objects.nonNull(electronResponse)) {
					invoker.run(electronRequest, electronResponse, null);
					return;
				}
				if (data.getStatus() == AuthResponseEnum.AUTH_RESULT_SUCCESS) {
					electronRequest.setData(DataSpot.HEADER, ElectronConstant.USER_INFO_KEY_NAME, data.getUserData());
					electronRequest.setData(DataSpot.USER_KEY, ElectronConstant.USER_KEY, data.getUserkey());
					this.cache(data.getUserkey(), data.getUserData(), data.getCacheTime());
				} else {
					electronResponse = createElectronResponse(electronRequest,
							ExceptionType.SECURITY_AUTH_TOKEN_NOT_FAIL, data.getResultData());
				}
				invoker.run(electronRequest, electronResponse, null);
			}
		}, authRequestData);
		// 6. 判断认证是否通过
		return ElectronResponse.ANSY_RESPONSE;
	}

	private ElectronResponse createElectronResponse(ElectronRequest electronRequest, ExceptionType exceptionType,
			Object... args) {
		Authentication authentication = getAbilityData();
		ElectronResponse electronResponse;
		if (authentication.getRedirectSpot() == DataSpot.REDIRECT) {
			electronResponse = exceptionType.wrapper(electronRequest, HttpResponseStatus.FOUND, args);
			electronResponse.setData(DataSpot.HEADER, HttpHeaderNames.LOCATION.toString(),
					authentication.getRedirectData());
		} else {
			electronResponse = exceptionType.wrapper(electronRequest, HttpResponseStatus.OK,
					authentication.getRedirectData());
		}
		return electronResponse;
	}

	@Override
	public void setInsideServiceFactory(InsideServiceFactory insideServiceFactory) {
		this.serviceFactory = insideServiceFactory;

	}
}
