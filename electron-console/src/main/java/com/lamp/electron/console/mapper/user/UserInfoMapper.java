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
package com.lamp.electron.console.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lamp.electron.console.entity.user.UserInfo;

@Mapper
public interface UserInfoMapper {

	static String TABLE_SQL = " user_info ";

	static String INSET_SQL = "insert into " + TABLE_SQL;

	static String UPDATE_SQL = "update " + TABLE_SQL + " set ";

	static String SELECT_SQL = "select * from " + TABLE_SQL + " where ";

	/**
	 * 添加用户</br>
	 * 用户自己注册</br>
	 * 第三方登录，自动添加</br>
	 * 管理员添加</br>
	 * 管理员从第三方添加</br>
	 * 目前只有密码注册
	 * @param userInfo
	 * @return
	 */
	@Insert({INSET_SQL,
		"(ui_name,ui_nick_name,ui_sex,ui_age,ui_type,ui_birth,ui_address,ui_id_type,ui_id_card,ui_phone,ui_email,ui_password,ui_salt,ui_salt_password)values",
		"(#{uiName},#{uiNickname},#{uiSex},#{uiAge},#{uiType},#{uiBirth},#{uiAddress},#{uiIdType},#{uiIdCard},#{uiPhone},#{uiEmail},#{uiPassword},#{uiSalt},#{uiSaltPassword})"
		})
	public Integer insertUserInfo(UserInfo userInfo);

	/**
	 * 查询单个用户。用户登录，邮箱登录，用户名登录。
	 * 
	 * @param userInfo
	 * @return
	 */
	@Select({ "<script>", "select ui_id, ui_name,ui_nick_name,ui_sex,ui_age,ui_type,ui_address,ui_id_type,ui_id_card,ui_phone,ui_email,ui_salt,ui_salt_password,ui_password from user_info where",
		"<if test='uiName != null'> ui_name = #{uiName}</if>", 
		"<if test='uiPhone != null'> ui_phone = #{uiPhone}</if>",
		"<if test='uiEmail != null'> ui_email = #{uiEmail}</if>",
		"</script>" })
	public UserInfo queryUserInfoByLogin(UserInfo userInfo);

	/**
	 * 表单查询
	 * 
	 * @param userInfo
	 * @return
	 */
	@Select({
		"<script>", 
		"select ui_name,ui_nick_name,ui_sex,ui_age,ui_type,ui_address,ui_id_type,ui_id_card,ui_phone,ui_email, ui_token  from",
		TABLE_SQL,
		" <where> ",
		"<if test='uiName != null'> ui_name like \"%\"#{uiName}\"%\"</if>",
		" </where> ",
		"</script>" 
	})
	public List<UserInfo> queryUserInfoByForm(UserInfo userInfo);
	
	@Select({ "select ui_name,ui_nick_name,ui_sex,ui_age,ui_type,ui_address,ui_id_type,ui_id_card,ui_phone,ui_email, ui_token ",
		"from ", TABLE_SQL ,"where ui_id = #{uiId}"
	})
	public UserInfo queryUserInfoByUiId(UserInfo userInfo);
	
	
	
	/**
	 * 更新用户状态。激活，注销
	 * 
	 * @param userInfo
	 * @return
	 */
	@Update({UPDATE_SQL,"set ",""})
	public Integer updateUserInfoState(UserInfo userInfo);

	/**
	 * 用户退出使用
	 * 
	 * @param userInfo
	 * @return
	 */
	@Update({UPDATE_SQL , "set ui_forbid_time = now()"," where ui_id = #{uiId}"})
	public Integer updateUserSignOutByUiId(UserInfo userInfo);

	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public Integer updateUserInfoByUiId(UserInfo userInfo);

	
	/**
	 * 登录成功后修改登录信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@Update({UPDATE_SQL , "ui_token = #{uiToken}, ui_login_time = now()"," where ui_id = #{uiId}"})
	public Integer updateLoginById(UserInfo userInfo);
	
	@Update({UPDATE_SQL , " ui_salt_password = #{uiSaltPassword},ui_password = #{uiPassword}"," where ui_id = #{uiId}"})
	public Integer updatePasswordByUiId(UserInfo userInfo);

}
