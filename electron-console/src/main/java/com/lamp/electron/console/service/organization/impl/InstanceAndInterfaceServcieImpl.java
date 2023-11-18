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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.lamp.electron.base.common.register.data.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lamp.electron.base.common.basedata.NodeBase;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.register.data.CodeInstance;
import com.lamp.electron.base.common.register.data.InterfaceInfo;
import com.lamp.electron.base.common.register.server.CodeInstanceRegister;
import com.lamp.electron.base.common.register.server.InstanceRegister;
import com.lamp.electron.base.common.register.server.InterfaceRegister;
import com.lamp.electron.console.mapper.organization.InstanceAndInterfaceMapper;
import com.lamp.electron.console.service.organization.InstanceAndInterfaceServcie;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class InstanceAndInterfaceServcieImpl implements InstanceAndInterfaceServcie {

	void supplement(NodeBase nodeBase) {
		if (Objects.isNull(nodeBase.getLanguage())) {
			nodeBase.setLanguage("Java");
		}
		if (Objects.isNull(nodeBase.getInstanceType())) {
			nodeBase.setInstanceType("business");
		}
		if (Objects.isNull(nodeBase.getRPCType())) {
			nodeBase.setRPCType("http");
		}
		if (Objects.isNull(nodeBase.getVersion())) {
			nodeBase.setVersion("0.0.1");
		}
		if (Objects.isNull(nodeBase.getGatherData())) {
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			nodeBase.setGatherData(dateFm.format(new Date()));
		}
		if (nodeBase.getApplicationId() == null) {
			nodeBase.setApplicationId(1L);
		}
	}

	@Autowired
	private InstanceAndInterfaceMapper instanceAndInterfaceMapper;

	/**
	 * 实例与应用（组织）关联，关联id是 1. Instance 可以通过时间，应用名，时间，ip，端口，建一个唯一建 2. interface 可以通过
	 * path，版本号，
	 */
	public Integer insertNodeBase(NodeBase nodeBase) {
		try {
			this.supplement(nodeBase);
			if (Objects.isNull(nodeBase.getOrganizationTypeEnum())) {
				return instanceAndInterfaceMapper.insertNodeBase(nodeBase);
			}
			return Objects.equals(nodeBase.getOrganizationTypeEnum(), OrganizationTypeEnum.INSTANCE)
					? instanceAndInterfaceMapper.insertNodeBase(nodeBase)
					: instanceAndInterfaceMapper.insertInterface(nodeBase);
		} catch (Exception e) {
			log.error(nodeBase.toString());
			log.error(e.getMessage(), e);
			return -1;
		}
	}

	public Integer updateNodeOfflineStuats(NodeBase nodeBase) {
		return null;
	}

	@Override
	public Integer updateNodeBase(InterfaceInfo interfaceInfo) {
		return instanceAndInterfaceMapper.updateNodeBase(interfaceInfo);
	}

	@Override
	public NodeBase queryNodeBaseById(InterfaceInfo interfaceInfo) {
		return instanceAndInterfaceMapper.queryNodeBaseById(interfaceInfo);
	}

	@Override
	public List<NodeBase> queryNodeBaseListByOiId(InterfaceInfo interfaceInfo) {
		return instanceAndInterfaceMapper.queryNodeBaseListByOiId(interfaceInfo);
	}

	@Override
	public List<NodeBase> queryNodeBaseListByFrom(InterfaceInfo interfaceInfo) {
		return Objects.equals(interfaceInfo.getOrganizationTypeEnum(), OrganizationTypeEnum.INSTANCE)
				? instanceAndInterfaceMapper.queryNodeBaseListByFrom(interfaceInfo)
				: instanceAndInterfaceMapper.queryInterfaceInfoListByFrom(interfaceInfo);
	}

	@Component
	static class ConsoleInterfaceRegister implements InterfaceRegister {

		@Autowired
		private InstanceAndInterfaceServcie instanceAndInterfaceServcie;

		@Override
		public int register(InterfaceInfo t) {
			t.setHttpMethodTypeString(JSON.toJSONString(t.getHttpMethodType()));
			return instanceAndInterfaceServcie.insertNodeBase(t);
		}

		@Override
		public int deregister(InterfaceInfo t) {
			return 0;
		}

	}

	@Component
	static class ConsoleInstanceRegister implements InstanceRegister {

		@Autowired
		private InstanceAndInterfaceServcie instanceAndInterfaceServcie;

		@Override
		public int register(InstanceInfo t) {

			return instanceAndInterfaceServcie.insertNodeBase(t);
		}

		@Override
		public int deregister(InstanceInfo t) {
			return instanceAndInterfaceServcie.updateNodeOfflineStuats(t);
		}
	}

	@Component
	static class ConsoleCodeInstanceRegister implements CodeInstanceRegister {

		@Autowired
		private InstanceAndInterfaceServcie instanceAndInterfaceServcie;

		@Override
		public int register(CodeInstance t) {
			return instanceAndInterfaceServcie.insertNodeBase(t);
		}

		@Override
		public int deregister(CodeInstance t) {
			return instanceAndInterfaceServcie.updateNodeOfflineStuats(t);
		}

	}

}
