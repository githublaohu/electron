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
package com.lamp.electron.core.container;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

public class ContainerBeanFactory {

	private Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

	private Map<Class<?>, RelyRelationship> beanRelyRelationship = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<?> clazz) {
		return (T) beanMap.get(clazz);
	}

	public void setBean(Object object) {
		beanMap.put(object.getClass(), object);
	}

	public void rely(Object object) throws IllegalArgumentException, IllegalAccessException {
		RelyRelationship relationship = relationship(object.getClass());
		for(RelationshipMapper relationshipMapper : relationship.relationshipMapperList) {
			Object bean = beanMap.get(relationshipMapper.relyClass);
			relationshipMapper.accessibleObject.set(object, bean);
		}
	}

	public RelyRelationship relationship(Class<?> clazz) {
		RelyRelationship relyRelationship = beanRelyRelationship.get(clazz);
		if (Objects.isNull(relyRelationship)) {
			relyRelationship = beanRelyRelationship.computeIfAbsent(clazz, key -> {
				RelyRelationship newRelyRelationship = new RelyRelationship();
				List<RelationshipMapper> list = new ArrayList<>();
				for (Field field : clazz.getFields()) {
					Resource resource = field.getAnnotation(Resource.class);
					if (Objects.isNull(resource)) {
						RelationshipMapper relationshipMapper = new RelationshipMapper();
						relationshipMapper.accessibleObject = field;
						relationshipMapper.relyClass = field.getType();
						list.add(relationshipMapper);
					}
				}
				List<RelationshipMapper> newList = new ArrayList<>(list.size());
				newList.addAll(list);
				newRelyRelationship.relationshipMapperList = newList;
				return newRelyRelationship;
			});
		}
		return relyRelationship;
	}

	class RelyRelationship {
		private List<RelationshipMapper> relationshipMapperList;
	}

	class RelationshipMapper {

		private Field accessibleObject;

		private Class<?> relyClass;

		private String relyName;

		private String type;
	}
}
