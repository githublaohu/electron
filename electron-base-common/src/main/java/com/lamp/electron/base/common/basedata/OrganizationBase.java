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
package com.lamp.electron.base.common.basedata;

import com.lamp.electron.base.common.enums.OrganizationTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrganizationBase extends EnvironmentalBase {

	private Long departmentId = 0L;

	private String departmentName = "default";

	private Long teanId = 0L;

	private String teanName = "default";

	private Long applicationId;

	private String applicationName;

	private String applicationEnglishName;

	/**
	 * 组织id
	 */
	private Long organizationId;

	/**
	 * 组织名
	 */
	private String organizationName;
	
	/**
	 * 英文名
	 */
	private String organizationEnglistName;

	/**
	 * 组织类型
	 */
	private OrganizationTypeEnum organizationTypeEnum;
}
