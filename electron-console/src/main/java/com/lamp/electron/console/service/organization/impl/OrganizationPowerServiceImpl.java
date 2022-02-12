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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.electron.console.entity.organization.OrganizationPower;
import com.lamp.electron.console.mapper.organization.OrganizationPowerMapper;
import com.lamp.electron.console.service.organization.OrganizationPowerService;

@Service
@Transactional
public class OrganizationPowerServiceImpl implements OrganizationPowerService{

	
	@Autowired
	private OrganizationPowerMapper organizationPowerMapper;
	
	@Override
	public Integer insertOrganizationPower(OrganizationPower organizationPower) {
		return organizationPowerMapper.insertOrganizationPower(organizationPower);
	}

	@Override
	public Integer updateUserPowerByOpId(OrganizationPower organizationPower) {
		return organizationPowerMapper.updateOrganizationPowerStatusByOpId(organizationPower);
	}
	
	@Override
	public Integer updateOrganizationPowerStatusByOpId(OrganizationPower organizationPower) {
		return organizationPowerMapper.updateOrganizationPowerStatusByOpId(organizationPower);
	}

	@Override
	public List<OrganizationPower> queryOrganizationPowerByOiId(OrganizationPower organizationPower) {
		return organizationPowerMapper.queryOrganizationPowerByOiId(organizationPower);
	}


}
