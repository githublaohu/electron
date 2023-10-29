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
package com.lamp.electron.base.common.register.data;

import com.lamp.electron.base.common.basedata.BaseEntity;
import com.lamp.electron.base.common.enums.AbilityPower;
import com.lamp.electron.base.common.enums.AbilityTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AbilityInfo extends BaseEntity {

	/**
	 * 能力ID
	 */
	private Long aiId;

	/**
	 * 父ID
	 */
	private Long aiParentId;

	/**
	 * 能力名称
	 */
	private String aiName;

	/**
	 * 标签
	 */
	private String aiLabel;

	/**
	 * 能力类型
	 */
	private AbilityTypeEnum aiAbilityType;

	/**
	 * 能力数据
	 */
	private Object aiData;

	/**
	 * 能力说明
	 */
	private String aiDescription;

	/**
	 * 能力是否启用
	 */
	private String aiRelationStatus;

	/**
	 * 能力绑定次数
	 */
	private Long aiBindTimes;
	
	/**
	 * 能力权限
	 */
	private AbilityPower aiRangePower;
}
