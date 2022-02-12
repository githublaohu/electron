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

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * perception的目的是解决client动态话与client配置动态化
 * 1. 根节点
 * 2. 子节点
 * 3. 末节点
 * 4. 节点建立
 * 5. 根节点添加
 * 6. 子节点添加
 * 7. 末节点添加
 * 8. 根节点删除
 * 9. 子节点删除
 * 10. 末节点删除
 * 
 * @author laohu
 * 
 */
public class Perception<T> {

	private volatile T perceptionObject;

	private List<Perception<T>> childPerception;

	private Perception<T> parentPerception;

	private JSONObject perceptionJSONObject;

	private JSONObject currentJSONObject;

	private PerceptionTypeEnum perceptionTypeEnum;
	
	private Class<?> clazz;

	public Perception() {
		this.perceptionTypeEnum = PerceptionTypeEnum.ALONE;
	}

	public Perception(T perceptionObject) {
		this(null, perceptionObject);
	}
	
	public Perception(T perceptionObject,Class<?> clazz) {
		this(null, perceptionObject,clazz);
	}

	public Perception(Perception<T> parentPerception, T perceptionObject) {
		this(parentPerception, perceptionObject, perceptionObject.getClass());
	}
	
	/**
	 * chin创建
	 * 自动识别
	 * @param parentPerception
	 * @param pereptionObject
	 */
	public Perception(Perception<T> parentPerception, T perceptionObject,Class<?> clazz) {
		this.parentPerception = parentPerception;
		this.perceptionTypeEnum = Objects.isNull(parentPerception)?PerceptionTypeEnum.ROOT_NODE:PerceptionTypeEnum.LAST_NODE;
		this.clazz = clazz;
		// 根节点，没有任何数据
		currentJSONObject = goEmpty(perceptionObject);
		if (Objects.isNull(parentPerception)) {
			this.perceptionObject = perceptionObject;
			this.perceptionJSONObject = new JSONObject();
			this.perceptionJSONObject.putAll(currentJSONObject);
			return;
		}
		JSONObject perceptionJSONObject = this.parentPerception.getPerceptionJSONObject();
		if (Objects.isNull(perceptionJSONObject)) {
			this.perceptionObject = perceptionObject;
			this.perceptionJSONObject = new JSONObject();
			this.perceptionJSONObject.putAll(currentJSONObject);
		} else {
			this.perceptionJSONObject = new JSONObject();
			this.perceptionJSONObject.putAll(perceptionJSONObject);
			this.perceptionJSONObject.putAll(currentJSONObject);
			createPereptionObject();
		}
		if (Objects.nonNull(parentPerception)) {
			parentPerception.addChildPerception(this);
		}

	}

	public void addChildPerception(Perception<T> parentPerception) {
		if (Objects.isNull(childPerception)) {
			synchronized (this) {
				if(Objects.nonNull(childPerception)) {
					childPerception = new CopyOnWriteArrayList<>();
					this.perceptionTypeEnum = PerceptionTypeEnum.CHILD_NODE;
				}
			}
		}
		this.childPerception.add(parentPerception);
	}

	public void setPereptionObject(T perceptionObject) {
		if (perceptionTypeEnum == PerceptionTypeEnum.ALONE) {
			this.perceptionObject = perceptionObject;
			return;
		}

		synchronized (this) {
			for (String key : currentJSONObject.keySet()) {
				this.perceptionJSONObject.remove(key);
			}
			currentJSONObject = goEmpty(perceptionObject);
			this.perceptionJSONObject.putAll(currentJSONObject);
			createPereptionObject();
		}
		executeChildPereption(this.perceptionJSONObject, this.perceptionObject);

	}

	public void deletePereptionObject() {
		if (perceptionTypeEnum == PerceptionTypeEnum.ALONE) {
			this.perceptionObject = null;
			return;
		}
		synchronized (this) {
			currentJSONObject.clear();
			this.perceptionJSONObject = this.parentPerception.getPerceptionJSONObject();
			createPereptionObject();
		}
		executeChildPereption(this.perceptionJSONObject, this.perceptionObject);
	}

	void childPereption(JSONObject perceptionJSONObject, T perceptionObject) {
		synchronized (this) {
			if (currentJSONObject.isEmpty()) {
				this.perceptionObject = perceptionObject;
			} else {
				this.perceptionJSONObject.clear();
				this.perceptionJSONObject.putAll(perceptionJSONObject);
				this.perceptionJSONObject.putAll(currentJSONObject);
				createPereptionObject();
			}
		}
		executeChildPereption(this.perceptionJSONObject, this.perceptionObject);
	}

	private void executeChildPereption(JSONObject perceptionJSONObject, T perceptionObject) {
		if (Objects.isNull(childPerception) || childPerception.isEmpty()) {
			return;
		}
		for (Perception<T> perception : childPerception) {
			perception.childPereption(perceptionJSONObject, perceptionObject);
		}
	}

	@SuppressWarnings("unchecked")
	private void createPereptionObject() {
		if (perceptionTypeEnum != PerceptionTypeEnum.ROOT_NODE && Objects.nonNull(perceptionObject)) {
			this.perceptionObject = (T) this.perceptionJSONObject.toJavaObject(clazz);
		}
	}

	JSONObject getPerceptionJSONObject() {
		return perceptionJSONObject;
	}

	public T pereptionObject() {
		return perceptionObject;
	}

	public PerceptionTypeEnum getPerceptionTypeEnum() {
		return this.perceptionTypeEnum;
	}

	private JSONObject goEmpty(T perceptionObject) {
		return Objects.isNull(perceptionObject) ? new JSONObject()
				: JSON.parseObject(JSON.toJSONString(perceptionObject));
	}
}
