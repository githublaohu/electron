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
package com.lamp.electron.register.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurrencyRegisterServer implements InvocationHandler {

	private List<RegisterServer<Object>> registerModelList = new ArrayList<>();




	

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (Objects.isNull(args) || Objects.isNull(args[0])) {
			return -1;
		}
		try {
			for (RegisterServer<Object> registerModel : registerModelList) {
				if ("register".equals(method.getName())) {
					registerModel.register(args[0]);
				} else {
					registerModel.unRegister(args[0]);
				}
			}
		} catch (Exception e) {

		}

		return 1;
	}
}
