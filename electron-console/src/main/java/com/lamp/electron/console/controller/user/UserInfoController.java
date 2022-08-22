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
package com.lamp.electron.console.controller.user;

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.electron.console.entity.user.UserInfo;
import com.lamp.electron.console.entity.user.UserInfo.InsertUserInfoByPassword;
import com.lamp.electron.console.service.user.UserInfoServce;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

	@Autowired
	private UserInfoServce userInfoSerice;

	@PostMapping("/insertUserInfoByPassword")
	public void insertUserInfoByPassword(@RequestBody @Validated(InsertUserInfoByPassword.class) UserInfo userInfo) {
		String uiSalt = UUID.randomUUID().toString().replace("-", "") + userInfo.getUiName().substring(2, 3);
		userInfo.setUiSalt(uiSalt.substring(3));
		String uiSaltPassword = DigestUtils.md5Hex(userInfo.getUiSalt() + userInfo.getUiPassword());
		userInfo.setUiSaltPassword(uiSaltPassword);
		userInfoSerice.insertUserInfo(userInfo);

	}

	public void insertUserInfoByEmail(UserInfo userInfo) {
		// 创建链接地址
		String uiSalt = UUID.randomUUID().toString().replace("-", "") + userInfo.getUiName().substring(2, 3);
		userInfo.setUiSalt(uiSalt);
		String uiSaltPassword = DigestUtils.md5Hex(userInfo.getUiSalt() + userInfo.getUiEmail());
		userInfo.setUiSaltPassword(uiSaltPassword);
		userInfoSerice.insertUserInfo(userInfo);
	}

	@PostMapping("/manageAndUpdateUserInfoByUiId")
	public void manageAndUpdateUserPasswordByUiId(UserInfo userInfo) {
		UserInfo newUserInfo = userInfoSerice.queryUserInfoByLogin(userInfo);
		String uiSaltPassword = DigestUtils.md5Hex(newUserInfo.getUiSalt() + userInfo.getUiPassword());
		userInfo.setUiSaltPassword(uiSaltPassword);
		userInfo.setUiId(newUserInfo.getUiId());
		userInfoSerice.updatePasswordByUiId(newUserInfo);
	}

	/**
	 * 用户修改个人信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@PostMapping("/updateUserInfoByUiId")
	public void updateUserInfoByUiId(@RequestBody UserInfo userInfo) {

	}

	@PostMapping("/queryUserInfoById")
	public UserInfo queryUserInfoById(@RequestBody UserInfo userInfo) {
		return userInfoSerice.queryUserInfoByUiId(userInfo);
	}

	@PostMapping("/queryUserInfoByForm")
	public List<UserInfo> queryUserInfoByForm(@RequestBody UserInfo userInfo) {
		return userInfoSerice.queryUserInfoByForm(userInfo);
	}
}
