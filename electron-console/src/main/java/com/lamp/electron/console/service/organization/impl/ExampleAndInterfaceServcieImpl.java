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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.electron.base.common.basedata.NodeBase;
import com.lamp.electron.base.common.register.data.CodeExample;
import com.lamp.electron.base.common.register.data.ExampleInfo;
import com.lamp.electron.base.common.register.data.InterfaceInfo;
import com.lamp.electron.base.common.register.server.CodeExampleRegister;
import com.lamp.electron.base.common.register.server.ExampleRegister;
import com.lamp.electron.base.common.register.server.InterfaceRegister;
import com.lamp.electron.console.mapper.organization.ExampleAndInterfaceMapper;
import com.lamp.electron.console.service.organization.ExampleAndInterfaceServcie;

@Service
@Transactional
public class ExampleAndInterfaceServcieImpl implements ExampleAndInterfaceServcie {

	@Autowired
	private ExampleAndInterfaceMapper exampleAndInterfaceMapper;

	@Override
	public Integer insertNodeBase(NodeBase nodeBase) {
		return exampleAndInterfaceMapper.insertNodeBase(nodeBase);
	}

	public Integer updateNodeOfflineStuats(NodeBase nodeBase) {
		return null;
	}

	@Override
	public Integer updateNodeBase(InterfaceInfo interfaceInfo) {
		return exampleAndInterfaceMapper.updateNodeBase(interfaceInfo);
	}

	@Override
	public NodeBase queryNodeBaseById(InterfaceInfo interfaceInfo) {
		return exampleAndInterfaceMapper.queryNodeBaseById(interfaceInfo);
	}

	@Override
	public List<NodeBase> queryNodeBaseListByOiId(InterfaceInfo interfaceInfo) {
		return exampleAndInterfaceMapper.queryNodeBaseListByOiId(interfaceInfo);
	}

	@Override
	public List<NodeBase> queryNodeBaseListByFrom(InterfaceInfo interfaceInfo) {
		return exampleAndInterfaceMapper.queryNodeBaseListByFrom(interfaceInfo);
	}

	@Component
	static class ConsoleInterfaceRegister implements InterfaceRegister {

		@Autowired
		private ExampleAndInterfaceServcie exampleAndInterfaceServcie;

		@Override
		public int register(InterfaceInfo t) {
			return exampleAndInterfaceServcie.insertNodeBase(t);
		}

		@Override
		public int unRegister(InterfaceInfo t) {
			return 0;
		}

	}

	@Component
	static class ConsoleExampleRegister implements ExampleRegister {

		@Autowired
		private ExampleAndInterfaceServcie exampleAndInterfaceServcie;

		@Override
		public int register(ExampleInfo t) {
			return 0;
		}

		@Override
		public int unRegister(ExampleInfo t) {
			return exampleAndInterfaceServcie.updateNodeOfflineStuats(t);
		}
	}

	@Component
	static class ConsoleCodeExampleRegister implements CodeExampleRegister {

		@Autowired
		private ExampleAndInterfaceServcie exampleAndInterfaceServcie;

		@Override
		public int register(CodeExample t) {
			return 0;
		}

		@Override
		public int unRegister(CodeExample t) {
			return exampleAndInterfaceServcie.updateNodeOfflineStuats(t);
		}

	}

}
