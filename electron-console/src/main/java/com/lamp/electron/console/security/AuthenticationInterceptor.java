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
package com.lamp.electron.console.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

	private AuthenticationAction<HttpServletRequest, HttpServletResponse> authenticationAction;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean isBoo = false;
		// 判断url 是否需要认证.不需要认证的可以线缓存
		if (authenticationAction.isAuthentication(request)) {
			// 获得token
			String token = authenticationAction.getToken(request);
			// 判断用户是否有权限
			if (authenticationAction.authPath(request, response, token, handler)) {
				// 判断用户是否有资源权限
				return authenticationAction.authResources(request, response, token);
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
