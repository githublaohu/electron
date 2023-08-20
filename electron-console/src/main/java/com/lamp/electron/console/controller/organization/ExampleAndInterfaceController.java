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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.electron.base.common.basedata.NodeBase;
import com.lamp.electron.base.common.register.data.InterfaceInfo;
import com.lamp.electron.console.service.organization.ExampleAndInterfaceServcie;

@RestController
@RequestMapping("/exampleAndInterface")
public class ExampleAndInterfaceController {

	@Autowired
	private ExampleAndInterfaceServcie exampleAndInterfaceServcie;

	/**
	 * 
	 * @param interfaceInfo
	 * @return
	 */
	@PostMapping("/queryNodeBaseById")
	public NodeBase queryNodeBaseById(@RequestBody InterfaceInfo interfaceInfo) {
		return exampleAndInterfaceServcie.queryNodeBaseById(interfaceInfo);
	}

	/**
	 * 
	 * @param interfaceInfo
	 * @return
	 */
	@PostMapping("/queryNodeBaseListByOiId")
	public List<NodeBase> queryNodeBaseListByOiId(@RequestBody InterfaceInfo interfaceInfo) {
		return exampleAndInterfaceServcie.queryNodeBaseListByOiId(interfaceInfo);
	}

	/**
	 * 
	 * @param interfaceInfo
	 * @return
	 */
	@PostMapping("/queryNodeBaseListByFrom")
	public List<NodeBase> queryNodeBaseListByFrom(@RequestBody InterfaceInfo interfaceInfo) {
		return exampleAndInterfaceServcie.queryNodeBaseListByFrom(interfaceInfo);
	}

	/**
	 * 直接读etcd
	 * 
	 * @param interfaceInfo
	 * @return
	 */
	@PostMapping("/queryOnLineExampleByOiName")
	public List<NodeBase> queryOnLineExampleByOiName(@RequestBody InterfaceInfo interfaceInfo) {
		return null;
	}

	/**
	 * 直接读etcd
	 * 
	 * @param interfaceInfo
	 * @return
	 */
	@PostMapping("/queryOnLineCodeExample")
	public List<NodeBase> queryOnLineCodeExample(@RequestBody InterfaceInfo interfaceInfo) {
		return null;
	}
}
