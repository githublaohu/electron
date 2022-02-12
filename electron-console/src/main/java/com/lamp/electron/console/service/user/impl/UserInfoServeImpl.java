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
package com.lamp.electron.console.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.electron.console.entity.user.UserInfo;
import com.lamp.electron.console.entity.user.UserLoginRecord;
import com.lamp.electron.console.mapper.user.UserInfoMapper;
import com.lamp.electron.console.service.user.UserInfoServce;
import com.lamp.electron.console.utils.EntityUtils;

@Service
@Transactional
public class UserInfoServeImpl implements UserInfoServce{

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Override
	public Integer insertUserInfo(UserInfo userInfo) {
		EntityUtils.makeUpUserInfo(userInfo);
		return userInfoMapper.insertUserInfo(userInfo);
	}

	@Override
	public UserInfo queryUserInfoByLogin(UserInfo userInfo) {
		return userInfoMapper.queryUserInfoByLogin(userInfo);
	}

	@Override
	public Integer updateUserInfoState(UserInfo userInfo) {
		return userInfoMapper.updateUserInfoState(userInfo);
	}

	@Override
	public Integer updateUserSignOutByUiId(UserInfo userInfo) {
		return userInfoMapper.updateUserInfoByUiId(userInfo);
	}

	@Override
	public Integer updateUserInfo(UserInfo userInfo) {
		return 1;
	}

	@Async
	@Override
	public Integer updateLoginById(UserInfo userInfo, UserLoginRecord userLoginRecord) {
		return userInfoMapper.updateLoginById(userInfo);
	}

	@Override
	public List<UserInfo> queryUserInfoByForm(UserInfo userInfo) {
		return userInfoMapper.queryUserInfoByForm(userInfo);
	}

	@Override
	public Integer updatePasswordByUiId(UserInfo userInfo) {
		return userInfoMapper.updatePasswordByUiId(userInfo);
	}

	@Override
	public UserInfo queryUserInfoByUiId(UserInfo userInfo) {
		return userInfoMapper.queryUserInfoByUiId(userInfo);
	}

	
}
