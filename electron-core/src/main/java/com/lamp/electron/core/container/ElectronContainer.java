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
package com.lamp.electron.core.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lamp.electron.base.common.basedata.NodeBase;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.perception.ConfigPerceptionFactory;
import com.lamp.electron.base.common.register.ElectronPrefixFactory;
import com.lamp.electron.core.ability.AbilityInvokerManage;
import com.lamp.electron.core.ability.collect.RequestRecordAbility;
import com.lamp.electron.core.ability.collect.StatisticsAbility;
import com.lamp.electron.core.ability.function.statistics.StatisticsCenter;
import com.lamp.electron.core.container.bean.TrafficDetailsBean;
import com.lamp.electron.core.manage.AbilityManage;
import com.lamp.electron.core.manage.InstanceManage;
import com.lamp.electron.core.manage.InterfaceManage;
import com.lamp.electron.core.service.InsideServiceFactory;
import com.lamp.electron.register.api.DefaultRegisterFactory;
import com.lamp.electron.register.api.RegisterFactory;
import com.lamp.electron.register.api.RegisterServer;
import com.lamp.electron.rpc.ElectronClientFactory;
import com.lamp.electron.rpc.api.RpcServer;
import com.lamp.electron.rpc.http.HttpServer;
import com.lamp.electron.rpc.message.DefaultMQProducerFactory;

public class ElectronContainer {
	
	private ContainerConfig containerConfig;

	private ThreadPoolExecutor threadPoolExecutor;

	private ElectronRpcHandle electronRpcHandle;

	private ConfigPerceptionFactory perceptionFactory;

	private Map<ProtocolEnum, RpcServer> rpcServerMap = new HashMap<>();

	private RegisterFactory registerFactory;

	private ElectronClientFactory electronClientFactory;

	private AbilityManage abilityManage;

	private InstanceManage instanceManage;

	private InterfaceManage interfaceManage;
	
	private InsideServiceFactory serviceFactory;
	
	private ContainerBeanFactory containerBeanFactory;

	/**
	 * 提供etcd集群，子节点。基本配置
	 * @param containerConfig
	 */
	public ElectronContainer(ContainerConfig containerConfig) throws Exception {
		// 初始化对象工厂
		this.containerConfig = containerConfig;
		this.threadPoolExecutor = new ThreadPoolExecutor(containerConfig.getHandleThreadNum(),
				containerConfig.getHandleThreadNum(), 30, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
		this.perceptionFactory = new ConfigPerceptionFactory();
		this.crateBeanFactory();
		this.function();
		this.createRpcServerAndClient();
		this.monitorAndRegister();
		serviceFactory.setElectronRpcHandle(electronRpcHandle);
		serviceFactory.setInterfaceManage(interfaceManage);
	}

	private void crateBeanFactory() throws IllegalArgumentException, IllegalAccessException {
		
		containerBeanFactory = new ContainerBeanFactory();
		containerBeanFactory.setBean(containerBeanFactory);
		containerBeanFactory.setBean(this.perceptionFactory);
		NodeBase nodeBase = new NodeBase();
		nodeBase.setNetworkAddress(containerConfig.getRocketMQNameService());
		nodeBase.setName("core");
		DefaultMQProducerFactory defaultMQProducerFactory = new DefaultMQProducerFactory(nodeBase,null);
		containerBeanFactory.setBean(defaultMQProducerFactory);
		StatisticsCenter statisticsCenter = new StatisticsCenter();
		containerBeanFactory.setBean(statisticsCenter);
		containerBeanFactory.rely(statisticsCenter);
		TrafficDetailsBean trafficDetailsBean = new TrafficDetailsBean();
		containerBeanFactory.setBean(trafficDetailsBean);
		containerBeanFactory.rely(trafficDetailsBean);
		
		containerBeanFactory.relationship(StatisticsAbility.class);
		containerBeanFactory.relationship(RequestRecordAbility.class);
		
		
		electronClientFactory = new ElectronClientFactory();
		serviceFactory = new InsideServiceFactory();
		containerBeanFactory.setBean(serviceFactory);
		
		registerFactory = new DefaultRegisterFactory(containerConfig.getRegister(), ElectronPrefixFactory.PREIFX_FACTORY,
				containerConfig.getContainerName());
		instanceManage = new InstanceManage(electronClientFactory);
		interfaceManage = new InterfaceManage(electronClientFactory);
		abilityManage = new AbilityManage(instanceManage, interfaceManage,null);
		containerBeanFactory.rely(abilityManage);
		abilityManage.init();
		abilityManage.setServiceFactory(serviceFactory);
		abilityManage.setContainerBeanFactory(containerBeanFactory);
	}

	private void monitorAndRegister() throws Exception {
		List<RegisterServer<?>> registerServerList = new ArrayList<>();
		registerServerList.add(instanceManage);
		registerServerList.add(interfaceManage);
		registerServerList.add(abilityManage);
		registerFactory.createMonitorObjectTo(registerServerList);

		// registerFactory.createRegisterObject(null);
	}

	private void function() {
		AbilityInvokerManage abilityInvokerManage = new AbilityInvokerManage(abilityManage, interfaceManage,
				instanceManage);
		electronRpcHandle = new ElectronRpcHandle(threadPoolExecutor, abilityInvokerManage);

	}

	private void createRpcServerAndClient() {
		if (Objects.nonNull(containerConfig.getHttpRpcServerConfig())) {
			HttpServer httpServer = new HttpServer(electronRpcHandle, containerConfig.getHttpRpcServerConfig());
			rpcServerMap.put(httpServer.rpcType(), httpServer);
		}
		electronClientFactory.setRpcHandle(electronRpcHandle);
		electronClientFactory.setPerceptionFactory(perceptionFactory);

	}
}
