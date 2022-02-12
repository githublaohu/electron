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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lamp.electron.console.entity.user.UserInfo;

/**
 * 后期引入redis
 * 引入用户与认证库
 * 1. 提供权限认证
 * 2. 提供资源认证
 * 认证平台与API平台打通
 * 提供spring mvc 用户与认证信息注入
 * 提供用户与认证信息的注入传输对象
 * 解决重复调用能力
 * 
 * @author laohu
 *
 */
public class SessionFactory {

	private static final SessionFactory SESSION_FACTORY = new SessionFactory();

	public static final SessionFactory getInstance() {
		return SESSION_FACTORY;
	}

	private final Map<Class<?>, Map<Object, Object>> cacheObject = new ConcurrentHashMap<>();

	private SessionFactory() {
	}

	@SuppressWarnings("unchecked")
	public void setCache(Object key, Object object) {
		Class<?> clazz = null;
		if (object instanceof List) {
			List<Object> list = ((List<Object>) object);
			if (list.isEmpty()) {
				return;
			}
			clazz = list.get(0).getClass();
		}
		if (object instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) object;
			if (map.isEmpty()) {
				return;
			}
			clazz = map.values().iterator().next().getClass();
		} else {
			clazz = object.getClass();
		}
		this.setCache(key, object, clazz);
	}

	public void setCache(Object key, Object object, Class<?> clazz) {
		Map<Object, Object> map = cacheObject.get(clazz);
		if (Objects.isNull(map)) {
			map = cacheObject.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>());
		}
		map.put(key, object);
	}

	public void deleteCache(Object key, Class<?> clazz) {
		Map<Object, Object> map = cacheObject.get(clazz);
		if (Objects.isNull(map)) {
			map = cacheObject.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>());
		}
		map.remove(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getCache(Object key, Class<?> clazz) {
		Map<Object, Object> map = cacheObject.get(clazz);
		if (Objects.isNull(map)) {
			map = cacheObject.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>());
		}
		return (T) map.get(key);
	}

	public UserInfo getUserInfo() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
				.getRequest();
		String authorization = request.getHeader("Authorization");
		return getCache(authorization, UserInfo.class);
	}
}
