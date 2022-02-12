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
package com.lamp.electron.base.common.perception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.electron.base.common.basedata.OrganizationBase;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.enums.ProtocolConfigEnum;

public class ConfigPerceptionFactory {

	private Map<Class<?>, Map<OrganizationTypeEnum, Map<String, Perception<Object>>>> configPerceptionMap = new ConcurrentHashMap<>();

	private Map<Class<?>, Map<String, Perception<Object>>> simplePerceptionMap = new ConcurrentHashMap<>();

	{
		for (ProtocolConfigEnum configEnum : ProtocolConfigEnum.values()) {
			configPerceptionMap.put(configEnum.getClazz(), new HashMap<>());
			for (OrganizationTypeEnum organizationTypeEnum : OrganizationTypeEnum.values()) {
				configPerceptionMap.get(configEnum.getClazz()).put(organizationTypeEnum, new ConcurrentHashMap<>());
			}
		}
	}

	public void setPerceptionRootObject(OrganizationTypeEnum organizationTypeEnum, Object object, String id) {
		this.setPerceptionObject(organizationTypeEnum, object, id, null, null, null);
	}

	/**
	 * 要组织链
	 * 
	 * @param organizationTypeEnum
	 * @param clazz
	 * @param object
	 * @param id
	 * @param parentOrganizationTypeEnum
	 * @param parentId
	 * @param perceptionTypeEnum
	 */
	public void setPerceptionObject(OrganizationTypeEnum organizationTypeEnum, Object object, String id,
			OrganizationTypeEnum parentOrganizationTypeEnum, String parentId, List<OrganizationBase> organizationBase) {
		Map<String, Perception<Object>> perceptionMap = configPerceptionMap.get(object.getClass())
				.get(organizationTypeEnum);

		Perception<Object> perception = perceptionMap.get(id);

		if (Objects.isNull(perception)) {
			initPerception(organizationTypeEnum, object.getClass(), object, id, parentOrganizationTypeEnum, parentId);
		} else {
			perception.setPereptionObject(object);
		}
	}

	public void remotePerceptionObject(OrganizationTypeEnum organizationTypeEnum, String id, Class<?> clazz) {
		Map<String, Perception<Object>> perceptionMap = configPerceptionMap.get(clazz).get(organizationTypeEnum);
		Perception<Object> perception = perceptionMap.get(id);
		perception.deletePereptionObject();
	}

	private Perception<Object> initPerception(OrganizationTypeEnum organizationTypeEnum, Class<?> clazz, Object object,
			String id, OrganizationTypeEnum parentOrganizationTypeEnum, String parentId) {

		clazz = Objects.nonNull(clazz) ? clazz : (object == null ? null : object.getClass());
		Map<OrganizationTypeEnum, Map<String, Perception<Object>>> organizationPerception = configPerceptionMap
				.get(clazz);

		Perception<Object> perception = getPerceptionObject(
				organizationPerception.get(OrganizationTypeEnum.SYSTEM_DEFAULT), null,
				OrganizationTypeEnum.SYSTEM_DEFAULT.name(), null,clazz);
		perception = getPerceptionObject(organizationPerception.get(OrganizationTypeEnum.APPLICATION), null, parentId,
				perception,clazz);
		if (parentOrganizationTypeEnum == OrganizationTypeEnum.INTERFACE) {
			return getPerceptionObject(organizationPerception.get(OrganizationTypeEnum.APPLICATION), object, id,
					perception,clazz);
		} else {
			return perception;
		}
	}

	private Perception<Object> getPerceptionObject(Map<String, Perception<Object>> perceptionMap, Object object,
			String id, Perception<Object> preanPerception, Class<?> clazz) {

		Perception<Object> perception = perceptionMap.get(id);
		if (Objects.isNull(perception)) {
			perception = perceptionMap.computeIfAbsent(id, key -> {
				if(OrganizationTypeEnum.SYSTEM_DEFAULT.name().equals(key) || Objects.isNull(object)) {
					return new Perception<Object>(object, clazz);
				}else {
					return new Perception<Object>(preanPerception,object);
				}
			});
		}
		return perception;
	}

	/**
	 * 要组织链
	 * 
	 * @param clazz
	 * @param id
	 * @param organizationTypeEnum
	 * @param parentOrganizationTypeEnum
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Perception<T> getPerception(Class<?> clazz, String id, OrganizationTypeEnum organizationTypeEnum,
			OrganizationTypeEnum parentOrganizationTypeEnum, String parentId, List<OrganizationBase> organizationBase) {
		Map<String, Perception<Object>> perceptionMap = configPerceptionMap.get(clazz).get(organizationTypeEnum);

		Perception<Object> perception = perceptionMap.get(id);
		if (Objects.isNull(perception)) {
			perception = initPerception(organizationTypeEnum, clazz, null, id, parentOrganizationTypeEnum, parentId);
		}

		return (Perception<T>) perception;
	}

	@SuppressWarnings("unchecked")
	private <T> Perception<T> createSimplePerception(Object object, String id, Class<?> clazz) {
		Map<String, Perception<Object>> perceptionMap = simplePerceptionMap.get(clazz);
		if (Objects.isNull(perceptionMap)) {
			perceptionMap = simplePerceptionMap.computeIfAbsent(clazz, key -> new ConcurrentHashMap<>());
		}
		Perception<Object> prception = perceptionMap.get(id);
		if (Objects.isNull(prception)) {
			prception = perceptionMap.computeIfAbsent(id, key -> new Perception<>());
		}
		if (Objects.nonNull(object)) {
			prception.setPereptionObject(object);
		}
		return (Perception<T>) prception;
	}

	public <T> Perception<T> createSimplePerception(Object object, String id) {
		return createSimplePerception(object, id, object.getClass());
	}

	/**
	 * 是否有默认值
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> Perception<T> getSimplePerception(Class<?> clazz, String id) {
		return this.createSimplePerception(null, id, clazz);
	}
}
