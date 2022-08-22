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

import com.lamp.electron.base.common.basedata.NodeBase;
import com.lamp.electron.base.common.register.data.InterfaceInfo;

public interface InstanceAndInterfaceServcie {

	/**
	 * 
	 * @param nodeBase
	 * @return
	 */
	public Integer insertNodeBase(NodeBase nodeBase);

	/**
	 * 修改
	 * @param interfaceInfo
	 * @return
	 */
	public Integer updateNodeBase(InterfaceInfo interfaceInfo);
	
	
	public Integer updateNodeOfflineStuats(NodeBase nodeBase);

	/**
	 * 
	 * @param nodeBase
	 * @return
	 */
	public NodeBase queryNodeBaseById(InterfaceInfo interfaceInfo);

	/**
	 * 
	 * @param nodeBase
	 * @return
	 */
	public List<NodeBase> queryNodeBaseListByOiId(InterfaceInfo interfaceInfo);

	/**
	 * 
	 * @param nodeBase
	 * @return
	 */
	public List<NodeBase> queryNodeBaseListByFrom(InterfaceInfo interfaceInfo);
}
