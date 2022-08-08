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

public class LocalHttpAuthenticationAction implements AuthenticationAction<HttpServletRequest,HttpServletResponse> {

	
	
	@Override
	public boolean isAuthentication(HttpServletRequest q) {
		return false;
	}

	@Override
	public String getToken(HttpServletRequest q) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean authPath(HttpServletRequest q, HttpServletResponse p, String userKey, Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean authResources(HttpServletRequest q,HttpServletResponse p, String userKey) {
		return true;
	}

}
