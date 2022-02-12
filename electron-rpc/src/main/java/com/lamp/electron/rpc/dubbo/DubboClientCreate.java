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
package com.lamp.electron.rpc.dubbo;

import java.util.List;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.base.common.register.data.InterfaceInfo;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.rpc.api.RpcHandle;
import com.lamp.electron.rpc.api.client.RpcClientCreate;

public class DubboClientCreate implements RpcClientCreate{

	private static final String APPLICATION_CONFIG_NAME = "electron-name";

	private static final String APPLICATION_OWNER = "electron";

	private static final String APPLICATION_ORGANIZATION = "electron";

	private ApplicationConfig applicationConfig;

	private volatile ReferenceConfigCache referenceConfigCache = ReferenceConfigCache.getCache();

	public DubboClientCreate() {
		this.applicationConfig = new ApplicationConfig(APPLICATION_CONFIG_NAME);
		this.applicationConfig.setOwner(APPLICATION_OWNER);
		this.applicationConfig.setOrganization(APPLICATION_ORGANIZATION);
	}

	public GenericService newGenericService(String url, String interfaceClass, int timeout) {
		
		ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
		referenceConfig.setApplication(applicationConfig);
		referenceConfig.setUrl(url);
		referenceConfig.setTimeout(timeout);
		referenceConfig.setGeneric("true");
		referenceConfig.setInterface(interfaceClass);
		referenceConfig.setAsync(true);

		GenericService genericService = referenceConfigCache.get(referenceConfig);
		return genericService;
	}
	
	public GenericService newGenericService(List<RegistryConfig> registries, String interfaceClass, int timeout) {

		ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
		referenceConfig.setApplication(applicationConfig);
		referenceConfig.setTimeout(timeout);
		referenceConfig.setGeneric("true");
		referenceConfig.setInterface(interfaceClass);
		referenceConfig.setAsync(true);

		GenericService genericService = referenceConfigCache.get(referenceConfig);
		return genericService;
	}

	@Override
	public Invoker createClient(RpcHandle rpcHandle, NetworkAddress networkAddress, Perception<RpcRequestConfig> rpcConfig) {
		InterfaceInfo interfaceInfo = (InterfaceInfo)networkAddress;
		return new DubboClient(rpcHandle, newGenericService(interfaceInfo.getUrl(), interfaceInfo.getClassName(), 30000));
	}
}
