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
package com.lamp.electron.console.model.dto;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserBaseDTO {

	@Length(min = 6, max = 16, message = "旧密码必须长度在大于6小于16")
	private String oldPassword;

	@Length(min = 6, max = 16, message = "新密码必须长度在大于6小于16")
	private String newPassword;

	@Length(min = 6, max = 16, message = "新密码必须长度在大于6小于16")
	private String newTwoPassword;
}
