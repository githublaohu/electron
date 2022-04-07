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

import com.lamp.electron.base.common.basedata.RegisterBase;
import com.lamp.electron.base.common.enums.AbilityType;
import com.lamp.electron.base.common.enums.ProtocolConfigEnums;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AbilityRelation extends RegisterBase {

	/**
	 * 策略API关系ID
	 */
	private Long arId;

	/**
	 * 策略ID
	 */
	private Long aiId;

	/**
	 * 策略名称
	 */
	private String aiName;

	/**
	 * 动作类型
	 */
	private AbilityType abilityTypeEnum;

	/**
	 * 协议配置类型
	 */
	private ProtocolConfigEnums protocelConfigEnum;

	/**
	 * 策略内容
	 */
	private Object ability;

	/**
	 * 绑定状态
	 */
	private Integer arRelationStatus;

	/**
	 * 说明
	 */
	private String arExplain;

}
