package com.lamp.electron.console;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.lamp.electron.console.entity.user.UserInfo;

public class UserInfoControllerTest {

	@Test
	public void createUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUiName("electron");
		userInfo.setUiNickname("electron");
		userInfo.setUiPassword("userInfo");
		userInfo.setUiAge(33);
		userInfo.setUiSex("男");
		userInfo.setUiBirth(LocalDateTime.now());
		String uiSalt = UUID.randomUUID().toString().replace("-", "") + userInfo.getUiName().substring(2, 3);
		userInfo.setUiSalt(uiSalt.substring(3));
		String uiSaltPassword = DigestUtils.md5Hex(userInfo.getUiSalt() + userInfo.getUiPassword());
		userInfo.setUiSaltPassword(uiSaltPassword);
		System.out.println(userInfo.getUiSalt());
		System.out.println(uiSaltPassword);
		System.out.println(userInfo.getUiName().substring(2, 3));

	}
}
