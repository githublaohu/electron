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
package com.lamp.electron.base.common.basedata;

import com.lamp.electron.base.common.ElectronVersion;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.register.data.NetworkAddress;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NodeBase extends RegisterBase implements NetworkAddress {

	private Long id;

	private String name;

	private String networkAddress;

	private String port;

	private ProtocolEnum protocol;

	private String language;
	
	private OrganizationTypeEnum nodeTypeEnum;

	private String exampleType;

	private String RPCType;

	private String version;

	private ElectronVersion clientVersion = ElectronVersion.CURRENT;
	
	private String registryCentre;
	
	private String realize;
	
	private Invoker invoker;
	
	private String gatherData;

	//todo 有Getter不需要下面几个方法

	@Override
	public String networkAddress() {
		return networkAddress;
	}

	@Override
	public String port() {
		return port;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ProtocolEnum protocol() {
		return protocol;
	}

	public String version() {
		return version;
	}
	
	public boolean equals(Object object) {
		if (object instanceof NodeBase) {
			NodeBase nodeBase = (NodeBase) object;
			if (this.name != null && nodeBase.name != null) {
				return name.equals(nodeBase.name);
			}
			if (this.networkAddress == null || this.port == null || nodeBase.networkAddress == null
					|| this.port == null) {
				return false;
			}
			if(this.networkAddress.equals(nodeBase.networkAddress) && this.port.equals(this.port)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String realize() {
		return realize;
	}

	@Override
	public Invoker invoker() {
		return invoker;
	}
}
