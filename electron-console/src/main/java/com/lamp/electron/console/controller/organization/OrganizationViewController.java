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
package com.lamp.electron.console.controller.organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.electron.console.entity.organization.OrganizationInfo;
import com.lamp.electron.console.service.organization.OrganizationInfoService;

@RestController
@RequestMapping("/organizationView")
public class OrganizationViewController {

	@Autowired
	private OrganizationInfoService organizationInfoService;
	
	public Object queryOrganizationView(OrganizationInfo organizationInfo ) {
		
		List<OrganizationInfo> organizationInfoList = organizationInfoService.queryOrganizationInfoByTypeAndSuperior(organizationInfo);
		organizationInfoList.get(0);
		return null;
	}
}
