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
package com.lamp.electron.console.service.organization.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.electron.console.entity.organization.OrganizationInfo;
import com.lamp.electron.console.mapper.organization.OrganizationInfoMapper;
import com.lamp.electron.console.service.organization.OrganizationInfoService;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationInfoService {

	@Autowired
	private OrganizationInfoMapper organizationMapper;
	
	public List<OrganizationInfo> queryOrganizationInfoByUserId(OrganizationInfo organizationInfo){
		return organizationMapper.queryOrganizationInfoByUserId(organizationInfo);
	}

	@Override
	public List<OrganizationInfo> queryOrganizationInfoByTypeAndSuperior(OrganizationInfo organizationInfo) {
		return organizationMapper.queryOrganizationInfoByTypeAndSuperior(organizationInfo);
	}

	@Override
	public Integer updateOwnerById(OrganizationInfo organizationInfo) {
		return organizationMapper.updateOwnerById(organizationInfo);
	}

	@Override
	public Integer updateExplainById(OrganizationInfo organizationInfo) {
		return organizationMapper.updateExplainById(organizationInfo);
	}

	@Override
	public Integer deleteOrganizationById(OrganizationInfo organizationInfo) {
		int result = organizationMapper.deleteOrganizationById(organizationInfo);
		if(result == 1) {
			updateSubordinateNumById(organizationInfo, -1);
		}
		return result;
	}

	@Override
	public Integer insertOrganizationInfo(OrganizationInfo organizationInfo) {
		updateSubordinateNumById(organizationInfo, 1);
		return organizationMapper.insertOrganizationInfo(organizationInfo);
	}

	private void updateSubordinateNumById(OrganizationInfo organizationInfo , Integer num) {
		if( !Objects.nonNull(organizationInfo.getOiSuperiorId()) || organizationInfo.getOiSuperiorId() == 0) {
			return;
		}
		organizationInfo.setOiSubordinateNum(num);
		organizationMapper.updateSubordinateNumById(organizationInfo);
	}
	
	@Override
	public OrganizationInfo queryOrganizationInfoByOiId(OrganizationInfo organizationInfo) {
		return organizationMapper.queryOrganizationInfoById(organizationInfo);
	}

}
