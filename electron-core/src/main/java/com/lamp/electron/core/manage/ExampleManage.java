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

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.electron.base.common.register.data.ExampleInfo;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.base.common.register.server.ExampleRegister;
import com.lamp.electron.rpc.ElectronClientFactory;

public class ExampleManage implements ExampleRegister {

	private final Map<String/**/ , LongRangeWrapper> nameToExample = new ConcurrentHashMap<>();

	private ElectronClientFactory electronClientFactory;

	public ExampleManage(ElectronClientFactory electronClientFactory) {
		this.electronClientFactory = electronClientFactory;
	}

	@Override
	public synchronized int register(ExampleInfo t) {
		String name = t.getApplicationEnglishName();

		LongRangeWrapper longRangeWrapper = nameToExample.get(name);
		if (Objects.isNull(longRangeWrapper)) {
			longRangeWrapper = new LongRangeWrapper(name, name, null);
			nameToExample.put(name, longRangeWrapper);
		}
		t.setInvoker(electronClientFactory.crateRpcClient(t, null, null));
		longRangeWrapper.addNetworkAddress(t);
		return 1;
	}

	@Override
	public synchronized int unRegister(ExampleInfo t) {
		String name = t.getApplicationEnglishName();
		LongRangeWrapper longRangeWrapper = nameToExample.get(name);
		if (Objects.nonNull(longRangeWrapper)) {
			longRangeWrapper.removeNetworkAddress(t);
		}
		return 1;
	}

	public LongRangeWrapper getExampleInfos(String applicationName) {
		return nameToExample.get(applicationName);
	}

}
