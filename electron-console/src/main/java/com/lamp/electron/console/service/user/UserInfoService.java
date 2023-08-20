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
package com.lamp.electron.console.service.user;

import java.util.List;

import com.lamp.electron.console.entity.user.UserInfo;
import com.lamp.electron.console.entity.user.UserLoginRecord;

public interface UserInfoService {


	/**
	 * 表单查询
	 * 
	 * @param userInfo
	 * @return
	 */
	public List<UserInfo> queryUserInfoByForm(UserInfo userInfo);

	/**
	 * 查询单个用户。用户登录，邮箱登录，用户名登录。
	 * 
	 * @param userInfo
	 * @return
	 */
	public UserInfo queryUserInfoByLogin(UserInfo userInfo);
	
	/**
	 * 
	 * @param userInfo
	 * @return
	 */
	public UserInfo queryUserInfoByUiId(UserInfo userInfo);

	
	/**
	 * 更新用户状态。激活，注销
	 * 
	 * @param userInfo
	 * @return
	 */
	public Integer updateUserInfoState(UserInfo userInfo);

	/**
	 * 用户退出使用
	 * 
	 * @param userInfo
	 * @return
	 */
	public Integer updateUserSignOutByUiId(UserInfo userInfo);

	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public Integer updateUserInfo(UserInfo userInfo);
	
	/**
	 * 登录成功后修改登录信息
	 * @param userInfo
	 * @return
	 */
	public Integer updateLoginById(UserInfo userInfo,UserLoginRecord userLoginRecord);

	
	/**
	 * 
	 * @param userInfo
	 * @return
	 */
	public Integer updatePasswordByUiId(UserInfo userInfo);

	
	/**
	 * 添加用户</br>
	 * 用户自己注册</br>
	 * 第三方登录，自动添加</br>
	 * 管理员添加</br>
	 * 管理员从第三方添加</br>
	 * 
	 * @param userInfo
	 * @return
	 */
	public Integer insertUserInfo(UserInfo userInfo);
}
