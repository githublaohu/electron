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
package com.lamp.electron.client.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.lamp.electron.base.common.constant.ElectronConstant;
import com.lamp.electron.client.AbstractTransmit;
import com.lamp.electron.client.ElectronProperties;

public class ElectonInterceptor extends AbstractTransmit implements HandlerInterceptor {

	private static final Log logger = LogFactory.getLog(ElectonInterceptor.class);

	public ElectonInterceptor(ElectronProperties electronProperties) throws ClassNotFoundException {
		super.init(electronProperties);
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			super.transmit(request.getHeader(ElectronConstant.USER_INFO_KEY_NAME));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}

}
