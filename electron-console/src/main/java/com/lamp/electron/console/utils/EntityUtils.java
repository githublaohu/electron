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
package com.lamp.electron.console.utils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lamp.electron.base.common.register.data.AbilityInfo;
import com.lamp.electron.console.entity.user.UserInfo;

public class EntityUtils {

	static Set<String> deleteKey = new HashSet<>();

	static {
		
		for (Field field : AbilityInfo.class.getDeclaredFields()) {
			deleteKey.add(field.getName());
		}
		Class<?> clazz = AbilityInfo.class.getSuperclass();
		while(clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				deleteKey.add(field.getName());
			}
			clazz = clazz.getSuperclass();
		}
	}

	public static void makeUpUserInfo(UserInfo userInfo) {
		userInfo.setUiSex("男");
		userInfo.setUiNickname(userInfo.getUiName());
		userInfo.setUiAge(100);
		userInfo.setUiBirth(LocalDateTime.now());
		userInfo.setUiAddress("北京市");
		userInfo.setUiEmail(userInfo.getUiSalt());
		userInfo.setUiPhone(userInfo.getUiSalt().substring(0, 12));
		userInfo.setUiHeadPortrait(userInfo.getUiSalt());
		userInfo.setUiIdType("身份证");
		userInfo.setUiIdCard(userInfo.getUiSalt().substring(0, 18));
		userInfo.setUiType("admin");
	}

	public static void abilityInfo(JSONObject json, AbilityInfo abilityInfo) {
		for (String key : deleteKey) {
			json.remove(key);
		}
		abilityInfo.setAiData(json.toJSONString());
	}

	public static List<JSONObject> abilityInfo(List<AbilityInfo> abilityInfoList) {
		List<JSONObject> jsonList = new ArrayList<>(abilityInfoList.size());
		for (AbilityInfo abilityInfo : abilityInfoList) {
			JSONObject json = (JSONObject) JSONObject.toJSON(abilityInfo);
			json.putAll(JSON.parseObject(abilityInfo.getAiData()));
			jsonList.add(json);
		}
		return jsonList;
	}
}
