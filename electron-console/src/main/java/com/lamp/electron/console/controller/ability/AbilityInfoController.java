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
package com.lamp.electron.console.controller.ability;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lamp.electron.base.common.register.data.AbilityInfo;
import com.lamp.electron.console.service.ability.AbilityInfoService;
import com.lamp.electron.console.utils.EntityUtils;

@RestController
@RequestMapping("/abilityInfo")
public class AbilityInfoController {

	@Autowired
	private AbilityInfoService abilityInfoService;

	@PostMapping("/insertAbilityInfo")
	public void insertAbilityInfo() {
		AbilityInfo abilityInfo = getPortraitInfo();
		abilityInfo.setAiRelationStatus("正常");
		abilityInfoService.insertAbilityInfo(abilityInfo);
	}

	@PostMapping("/deleteAbilityInfo")
	public void deleteAbilityInfo(@RequestBody AbilityInfo abilityInfo) {
		abilityInfoService.updateAbilityInfoStatus(abilityInfo);
	}

	@PostMapping("/queryAbilityInfoById")
	public Object queryAbilityInfoById(@RequestBody AbilityInfo abilityInfo) {
		return abilityInfoService.queryAbilityInfoById(abilityInfo);
	}
	
	@PostMapping("/queryAbilityInfoByForm")
	public Object queryAbilityInfoByForm(@RequestBody AbilityInfo abilityInfo) {
		return abilityInfoService.queryAbilityInfoByType(abilityInfo);
	}
	
	@PostMapping("/queryAbilityInfoByParentId")
	public Object queryAbilityInfoByParentId(@RequestBody AbilityInfo abilityInfo){
		return abilityInfoService.queryAbilityInfoByParentId(abilityInfo);
	}
	
	private AbilityInfo getPortraitInfo() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
				.getRequest();
		int len = request.getContentLength();
		byte[] buffer = new byte[len];

		try (ServletInputStream in = request.getInputStream()) {
			in.read(buffer, 0, len);
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String data = new String(buffer);
		AbilityInfo abilityInfo = JSON.parseObject(data, AbilityInfo.class);
		JSONObject json = JSON.parseObject(data, JSONObject.class);
		EntityUtils.abilityInfo(json, abilityInfo);
		return abilityInfo;
	}
	
	
}
