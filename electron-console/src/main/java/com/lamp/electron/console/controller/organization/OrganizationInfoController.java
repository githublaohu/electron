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
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.console.entity.organization.OrganizationInfo;
import com.lamp.electron.console.entity.user.UserInfo;
import com.lamp.electron.console.security.SessionFactory;
import com.lamp.electron.console.service.organization.OrganizationInfoService;

@RestController
@RequestMapping("/organization")
public class OrganizationInfoController {

	@Autowired
	private OrganizationInfoService organizationInfoService;

	@PostMapping("/queryOrganizationInfoByUserId")
	public List<OrganizationInfo> queryOrganizationInfoByUserId(){
		SessionFactory sessionFactory = SessionFactory.getInstance();
		UserInfo userInfo = sessionFactory.getUserInfo();
		OrganizationInfo organizationInfo = new OrganizationInfo();
		organizationInfo.setOiOwnerId(userInfo.getUiId());
		return organizationInfoService.queryOrganizationInfoByUserId(organizationInfo);
	}
	
	@PostMapping("/queryOrganizationInfoByOiId")
	public OrganizationInfo queryOrganizationInfoByOiId(@RequestBody OrganizationInfo organizationInfo) {
		return organizationInfoService.queryOrganizationInfoByUiId(organizationInfo);
	}
	
	@PostMapping("/queryOrganizationInfoByForm")
	public List<OrganizationInfo> queryOrganizationInfoByForm(@RequestBody OrganizationInfo organizationInfo) {
		return null;
	}
	
	/**
	 * 查询下属组织
	 * 
	 * @param organizationInfo
	 * @return
	 */
	@PostMapping("/queryOrganizationInfoByTypeAndSuperior")
	public List<OrganizationInfo> queryOrganizationInfoByTypeAndSuperior(@RequestBody OrganizationInfo organizationInfo) {
		return organizationInfoService.queryOrganizationInfoByTypeAndSuperior(organizationInfo);
	}

	/**
	 * 修改拥有人
	 * 
	 * @param organizationInfo
	 * @return
	 */
	@PostMapping("/updateOwnerById")
	public Integer updateOwnerById(@RequestBody OrganizationInfo organizationInfo) {
		return organizationInfoService.updateOwnerById(organizationInfo);
	}

	/**
	 * 修改组织说明
	 * 
	 * @param organizationInfo
	 * @return
	 */
	@PostMapping("/updateExplainById")
	public Integer updateExplainById(@RequestBody OrganizationInfo organizationInfo) {
		return organizationInfoService.updateExplainById(organizationInfo);
	}

	/**
	 * 作废组织，必须把下面所有组织一一作废，同时没有任何实例
	 * 
	 * @param organizationInfo
	 * @return
	 */
	@PostMapping("/deleteOrganizationById")
	public Integer deleteOrganizationById(@RequestBody OrganizationInfo organizationInfo) {
		return organizationInfoService.deleteOrganizationById(organizationInfo);
	}

	/**
	 * 添加组织
	 * 
	 * @param organizationInfo
	 * @return
	 */
	@PostMapping("/insertOrganizationInfo")
	public Integer insertOrganizationInfo(@RequestBody OrganizationInfo organizationInfo) {
		if(Objects.isNull(organizationInfo.getOiSuperiorId())) {
			organizationInfo.setOiSuperiorId(0L);
		}
		UserInfo userInfo = SessionFactory.getInstance().getUserInfo();
		organizationInfo.setOiCreaterId(userInfo.getUiId());
		organizationInfo.setOiCreaterName(userInfo.getUiNickname());
		organizationInfo.setOiOwnerId(userInfo.getUiId());
		organizationInfo.setOiOwnerName(userInfo.getUiNickname());
		if(OrganizationTypeEnum.SPACE == organizationInfo.getOiType()) {
			organizationInfo.setOiSuperiorId(0L);
		}
		return organizationInfoService.insertOrganizationInfo(organizationInfo);
	}
}
