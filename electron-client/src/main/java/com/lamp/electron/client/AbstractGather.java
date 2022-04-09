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
package com.lamp.electron.client;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.lamp.electron.base.common.register.server.InstanceRegister;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lamp.electron.base.common.RemotingUtil;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.metadate.TypeMetadate;
import com.lamp.electron.base.common.register.RegisterServerFocusCall;
import com.lamp.electron.base.common.register.data.InstanceInfo;
import com.lamp.electron.base.common.register.data.InterfaceInfo;
import com.lamp.electron.base.common.service.ServiceSign;

public abstract class AbstractGather {

	private static final Log logger = LogFactory.getLog(AbstractGather.class);

	protected AtomicInteger starUp = new AtomicInteger();

	private List<InterfaceInfo> interfaceInfoList = new ArrayList<>();

	private RegisterServerFocusCall registerServerFocusCall;

	private ElectronProperties electronProperties;

	private Integer port;

	private String networkAddress = RemotingUtil.getLocalAddress();

	private ProtocolEnum protocol;

	private String rpcFrameName;
	
	protected String RPCType;

	protected AbstractGather(String rpcFrameName) {
		this.rpcFrameName = rpcFrameName;
	}

	protected void setElectronProperties(ElectronProperties electronProperties) {
		this.electronProperties = electronProperties;
		if(Objects.nonNull(electronProperties.getNetAddress())) {
			networkAddress = electronProperties.getNetAddress();
		}
	}

	protected void setPort(Integer port) {
		this.port = port;
	}

	protected void setProtocolEnum(ProtocolEnum protocol) {
		this.protocol = protocol;
	}

	protected void verification() {
		if (Objects.isNull(electronProperties)) {
			logger.warn("Absolute zero, Electronic failure ");
			return;
		}
		if (Objects.isNull(port)) {
			logger.warn(rpcFrameName + " not start");
			return;
		}
		if (StringUtils.isEmpty(electronProperties.getApplicationName())) {
			logger.warn("electron start must applicationName");
			return;
		}
		starUp.incrementAndGet();
		registerServerFocusCall = new RegisterServerFocusCall(electronProperties.getRegistry(),
				electronProperties.getContainerName());
	}

	protected void registerInterface(String path, String[] httpMethodType, Method method) {
		InterfaceInfo interfaceInfo = doCreateInterface(path, method);
		interfaceInfo.setHttpMethodType(httpMethodType);
		interfaceInfoList.add(interfaceInfo);
	}

	protected void registerInterface(String path, List<TypeMetadate> typeMetadateList, String version, Method method) {
		this.registerInterface(path, typeMetadateList, null, version, method);
	}

	protected void registerInterface(String path, List<TypeMetadate> typeMetadateList, String url, String version,
			Method method) {
		InterfaceInfo interfaceInfo = doCreateInterface(path, method);
		interfaceInfo.setUrl(url);
		interfaceInfo.setParameter(typeMetadateList);
		// interfaceInfo.setVersion(version);
		interfaceInfoList.add(interfaceInfo);
	}

	private InterfaceInfo doCreateInterface(String path, Method method) {
		InterfaceInfo interfaceInfo = new InterfaceInfo();
		interfaceInfo.setClassName(method.getDeclaringClass().getName());
		interfaceInfo.setServierSign(ServiceSign.getIdentification(method.getDeclaringClass()));
		interfaceInfo.setMethodName(method.getName());
		interfaceInfo.setPath(path);
		interfaceInfo.setOrganizationEnglistName(electronProperties.getApplicationName());
		interfaceInfo.setApplicationEnglishName(electronProperties.getApplicationName());
		interfaceInfo.setApplicationName(electronProperties.getApplicationName());
		interfaceInfo.setNetworkAddress(networkAddress);
		interfaceInfo.setPort(port.toString());
		interfaceInfo.setProtocol(protocol);
		interfaceInfo.setVersion(electronProperties.getVersion());
		interfaceInfo.setOrganizationTypeEnum(OrganizationTypeEnum.INTERFACE);
		return interfaceInfo;
	}

	protected void register() {
		if (Objects.isNull(interfaceInfoList) || interfaceInfoList.isEmpty()) {
			return;
		}
		registerServerFocusCall.createInterfaceRegister().batchRegister(interfaceInfoList);
		InstanceInfo instanceInfo = new InstanceInfo();
		instanceInfo.setApplicationEnglishName(electronProperties.getApplicationName());
		instanceInfo.setApplicationName(electronProperties.getApplicationName());
		instanceInfo.setName(electronProperties.getApplicationName());
		instanceInfo.setNetworkAddress(networkAddress);
		instanceInfo.setPort(port.toString());
		instanceInfo.setRPCType(RPCType);
		instanceInfo.setProtocol(protocol);
		instanceInfo.setVersion(electronProperties.getVersion());
		instanceInfo.setOrganizationTypeEnum(OrganizationTypeEnum.INSTANCE);
		instanceInfo.setGaterDate(new Date().toString());
		InstanceRegister instanceRegister = registerServerFocusCall.createInstanceRegister();
		instanceRegister.register(instanceInfo);
	}
}
