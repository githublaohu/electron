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

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.decoration.core.result.ResultObject;
import com.lamp.electron.console.entity.user.UserInfo;
import com.lamp.electron.console.entity.user.UserLoginRecord;
import com.lamp.electron.console.model.dto.UserBaseDTO;
import com.lamp.electron.console.security.SessionFactory;
import com.lamp.electron.console.service.user.UserInfoService;

@RestController
@RequestMapping("/userOperation")
public class UserOperationController {

	@Autowired
	private UserInfoService userInfoSerice;

	/**
	 * 目前只有账户密码登录wa
	 * 
	 * @param userInfo
	 * @return
	 */
	@PostMapping("/psswordLogin")
	public ResultObject<UserInfo> psswordLogin(@RequestBody UserInfo userInfo, HttpServletResponse response,
			HttpServletRequest request) {
		UserInfo newUserInfo = userInfoSerice.queryUserInfoByLogin(userInfo);
		if (Objects.isNull(newUserInfo)) {
			return new ResultObject<UserInfo>(20000, "用户或者密码输入错误");
		}
		String uiSaltPassword = DigestUtils.md5Hex(newUserInfo.getUiSalt() + userInfo.getUiPassword());
		if (!Objects.equals(uiSaltPassword, newUserInfo.getUiSaltPassword())) {
			return new ResultObject<UserInfo>(20000, "用户或者密码输入错误");
		}
		// 生成token
		newUserInfo.setUiToken("lamp " + UUID.randomUUID().toString());
		// 修改userInfo,记录登录日志
		UserLoginRecord userLoginRecord = new UserLoginRecord();
		userLoginRecord.setUiId(newUserInfo.getUiId());
		userLoginRecord.setUlLoginTime(LocalDateTime.now());
		userLoginRecord.setUlIp("");
		userLoginRecord.setUlLoginSystem("electron");
		userLoginRecord.setUlLoginWay("密码登录");
		userLoginRecord.setUlLoginTerminal("web");
		userInfoSerice.updateLoginById(newUserInfo, userLoginRecord);
		SessionFactory.getInstance().setCache(newUserInfo.getUiToken(), newUserInfo);
		response.addHeader("Authorization", newUserInfo.getUiToken());

		// 是不是管理员
		newUserInfo.setUiPassword(null);
		newUserInfo.setUiSalt(null);
		newUserInfo.setUiSaltPassword(null);
		return new ResultObject<UserInfo>(200, "登录成功",newUserInfo);
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 */
	@PostMapping("/signOut")
	public String signOut() {
		SessionFactory sessionFactory = SessionFactory.getInstance();
		UserInfo userInfo = sessionFactory.getUserInfo();
		sessionFactory.deleteCache(userInfo.getUiToken(), UserInfo.class);
		return null;
	}

	/**
	 * 用户注销
	 * 
	 * @return
	 */
	public String cancellation() {
		return null;
	}

	/**
	 * 管理员注销用户
	 * 
	 * @return
	 */
	public String adminCancellation() {
		return null;
	}

	/**
	 * 直接修改密码
	 * 
	 * @param userBaseDTO
	 * @return
	 */
	@PostMapping("/changePassword")
	public String changePassword(@RequestBody @Validated UserBaseDTO userBaseDTO) {
		UserInfo userInfo = SessionFactory.getInstance().getUserInfo();
		UserInfo newUserInfo = new UserInfo();
		newUserInfo.setUiId(userInfo.getUiId());
		newUserInfo.setUiPassword(userBaseDTO.getNewPassword());
		String uiSaltPassword = DigestUtils.md5Hex(newUserInfo.getUiSalt() + userBaseDTO.getNewPassword());
		newUserInfo.setUiSaltPassword(uiSaltPassword);
		userInfoSerice.updatePasswordByUiId(newUserInfo);
		newUserInfo = userInfoSerice.queryUserInfoByUiId(newUserInfo);
		SessionFactory.getInstance().setCache(newUserInfo.getUiToken(), newUserInfo);
		return null;
	}

	/**
	 * 连接修改密码
	 * 
	 * @return
	 */
	public String connectChangePassword(UserInfo userInfo) {
		UserInfo newUserInfo = userInfoSerice.queryUserInfoByLogin(userInfo);
		if (Objects.isNull(newUserInfo)) {

		}
		if (!Objects.equals(userInfo.getUiSaltPassword(), newUserInfo.getUiSaltPassword())) {

		}
		String uiSaltPassword = DigestUtils.md5Hex(newUserInfo.getUiSalt() + userInfo.getUiPassword());
		newUserInfo.setUiSaltPassword(uiSaltPassword);
		return null;
	}
}
