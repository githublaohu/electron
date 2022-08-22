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
package com.lamp.electron.console.entity.organization;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.console.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class OrganizationInfo extends BaseEntity {

	private Long oiId;

	/**
	 * 上级id
	 */
	private Long oiSuperiorId;

	/**
	 * 组织名
	 */
	@Length(min =2, max = 10 ,message = "组织名在2到10个汉字")
	private String oiName;

	/**
	 * 住址英文名
	 */
	@Length(min =6, max = 36 ,message = "组织英文名在6到36个英文字母")
	private String oiEnglishName;

	/**
	 * 组织类型
	 */
	@NotNull(message = "组织类型不能为空")
	private OrganizationTypeEnum oiType;
	
	/**
	 * 机构标签
	 */
	private String oiLabel;

	/**
	 * 组织说明
	 */
	@Length(min =6, max = 255 ,message = "组织说明在6到255个汉子")
	private String  oiExplain;

	/**
	 * 子机构数量
	 */
	private Integer oiSubordinateNum;

	/**
	 * 创建人id
	 */
	private Long oiCreaterId;

	/**
	 * 创建人名
	 */
	private String oiCreaterName;

	/**
	 * 拥有人id
	 */
	private Long oiOwnerId;

	/**
	 * 拥有人名
	 */
	private String oiOwnerName;
}
