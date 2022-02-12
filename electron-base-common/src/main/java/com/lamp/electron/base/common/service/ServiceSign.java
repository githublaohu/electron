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
package com.lamp.electron.base.common.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.lamp.electron.base.common.service.authentication.AuthenticationService;

public class ServiceSign {

	private final static Map<Class<?>, Integer> CLASS_IDENTIFICATION = new HashMap<>();

	private final static Map<Integer, Class<?>> IDENTIFICATION_CLASS = new HashMap<>();

	private final static Map<Class<?>, Map<String, Method>> CLASS_METHOD = new HashMap<>();

	static {
		int inc = 0;
		CLASS_IDENTIFICATION.put(AuthenticationService.class, 1 << inc++);

		for (Entry<Class<?>, Integer> e : CLASS_IDENTIFICATION.entrySet()) {
			IDENTIFICATION_CLASS.put(e.getValue(), e.getKey());
		}
		for (Class<?> clazz : CLASS_IDENTIFICATION.keySet()) {
			Method[] methods = clazz.getDeclaredMethods();
			Map<String, Method> methodMap = new HashMap<>(methods.length);
			CLASS_METHOD.put(clazz, methodMap);
			for (Method method : methods) {
				methodMap.put(method.getName(), method);
			}
		}
	}

	public static Integer getIdentification(Class<?> clazz) {
		if(clazz.isInterface()) {
			return CLASS_IDENTIFICATION.get(clazz);
		}
		Class<?>[] classArray = clazz.getInterfaces();
		if(Objects.isNull(classArray) || classArray.length == 0) {
			return null;
		}
		for(int i = 0 ; i< classArray.length ; i++) {
			 Integer identification = CLASS_IDENTIFICATION.get(classArray[i]);
			 if(Objects.nonNull(identification)) {
				 return identification;
			 }
		}
		return null;
	}

	public static Class<?> getService(Integer identification) {
		return IDENTIFICATION_CLASS.get(identification);
	}

	public static Method getServiceByMethod(Class<?> clazz, String methodName) {
		Map<String, Method> methodMap = CLASS_METHOD.get(clazz);
		if (Objects.isNull(methodMap)) {
			return null;
		}
		return methodMap.get(methodName);
	}

	public static Map<String, Method> getServiceByMethod(Class<?> clazz) {
		return CLASS_METHOD.get(clazz);
	}

}
