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
package com.lamp.electron.base.common.register.data;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;

@Getter
public class LongRangeWrapper {

	private List<NetworkAddress> networkAddressList = new CopyOnWriteArrayList<>();

	private String path;

	private String applicationName;

	private AtomicLong index;

	private LongRangeWrapper agent;

	public LongRangeWrapper() {

	}

	public LongRangeWrapper(String path, String applicationName, AtomicLong index) {
		this.path = path;
		this.applicationName = applicationName;
		this.index = index;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setAgent(LongRangeWrapper agent) {
		this.agent = agent;
	}

	public List<NetworkAddress> getNetworkAddress() {
		return networkAddressList;
	}

	public void addNetworkAddress(NetworkAddress networkAddress) {
		if (Objects.isNull(networkAddress)) {
			return;
		}
		if(Objects.isNull(agent)) {
			networkAddressList.add(networkAddress);
		}else {
			agent.addNetworkAddress(networkAddress);
		}
	}

	public void removeNetworkAddress(NetworkAddress networkAddress) {
		if (networkAddressList.isEmpty()) {
			return;
		}
		if(Objects.isNull(agent)) {
			networkAddressList.remove(networkAddress);
		}else {
			agent.removeNetworkAddress(networkAddress);
		}
	}
	
	public boolean isExistenceExample() {
		if(!networkAddressList.isEmpty()) {
			return true;
		}
		if(Objects.nonNull(agent) && agent.isExistenceExample()) {
			return true;
		}
		return false;
	}

}
