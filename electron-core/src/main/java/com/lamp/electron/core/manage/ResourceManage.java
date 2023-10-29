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
package com.lamp.electron.core.manage;

import com.lamp.electron.base.common.register.data.ResourceInstance;
import com.lamp.electron.base.common.register.server.ResourceInstanceRegister;
import com.lamp.electron.rpc.ElectronClientFactory;

public class ResourceManage implements ResourceInstanceRegister {

	
	private ElectronClientFactory electronClientFactory;
	
	@Override
	public int register(ResourceInstance t) {
		
		return 0;
	}

	@Override
	public int deregister(ResourceInstance t) {
		
		return 0;
	}

}
