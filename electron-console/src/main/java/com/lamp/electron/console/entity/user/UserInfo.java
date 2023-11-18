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
package com.lamp.electron.console.entity.user;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserInfo {

	/**
	 * 用户id
	 */
	private Long uiId;

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名必能为null", groups = { InsertUserInfoByPassword.class })
	@Length(min = 6, max = 16, message = "用户名必须长度在大于6小于16", groups = { InsertUserInfoByPassword.class })
	private String uiName;

	/**
	 * 用户倪晨
	 */
	private String uiNickname;

	/**
	 * 用户头像
	 */
	private String uiHeadPortrait;

	/**
	 * 用户性别
	 */
	private String uiSex;

	/**
	 * 用户年龄
	 */
	private Integer uiAge;

	/**
	 * 用户类型
	 */
	private String uiType;

	/**
	 * 出生年月
	 */
	private LocalDateTime uiBirth;

	/**
	 * 居住地点
	 */
	private String uiAddress;

	/**
	 * 
	 */
	private String uiIdType;
	
	/**
	 * 身份证号
	 */
	private String uiIdCard;

	/**
	 * 手机号
	 */
	private String uiPhone;

	/**
	 * 邮箱
	 */
	private String uiEmail;

	/**
	 * 原始密码
	 */
	@NotBlank(message = "用户名必能为null", groups = { InsertUserInfoByPassword.class })
	@Length(min = 6, max = 16, message = "密码必须长度在大于6小于16", groups = { InsertUserInfoByPassword.class })
	private String uiPassword;

	/**
	 * 盐
	 */
	private String uiSalt;

	/**
	 * 盐密
	 */
	private String uiSaltPassword;

	/**
	 * 当前token
	 */
	private String uiToken;

	/**
	 * 
	 */
	private String uiLoginRecord;

	/**
	 * 登录时间
	 */
	private String uiLoginTime;

	/**
	 * 用户退出时间
	 */
	private String uiForbidTime;

	/**
	 * 用户备注
	 */
	private String uiExtend;

	public static interface InsertUserInfoByPassword {
	}

	public UserInfo(Long uiId) {
		this.uiId = uiId;
	}

}
