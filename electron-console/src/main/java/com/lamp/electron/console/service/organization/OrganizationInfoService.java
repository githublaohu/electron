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
package com.lamp.electron.console.service.organization;

import java.util.List;

import com.lamp.electron.console.entity.organization.OrganizationInfo;

public interface OrganizationInfoService {

	
	List<OrganizationInfo> queryOrganizationInfoByUserId(OrganizationInfo organizationInfo);
	
	/**
	 * 查询用户能关系到的组织
	 * @param organizationInfo
	 * @return
	 */
	OrganizationInfo queryOrganizationInfoByUiId(OrganizationInfo organizationInfo);
	
	/**
	 * 查询下属组织
	 * @param organizationInfo
	 * @return
	 */
	List<OrganizationInfo> queryOrganizationInfoByTypeAndSuperior(OrganizationInfo organizationInfo);

	/**
	 * 修改拥有人
	 * @param organizationInfo
	 * @return
	 */
	Integer updateOwnerById(OrganizationInfo organizationInfo);
	
	/**
	 * 修改组织说明
	 * @param organizationInfo
	 * @return
	 */
	Integer updateExplainById(OrganizationInfo organizationInfo);
	
	/**
	 * 作废组织，必须把下面所有组织一一作废，同时没有任何实例
	 * @param organizationInfo
	 * @return
	 */
	Integer deleteOrganizationById(OrganizationInfo organizationInfo);
	
	/**
	 * 添加组织
	 * @param organizationInfo
	 * @return
	 */
	Integer insertOrganizationInfo(OrganizationInfo organizationInfo);
}
