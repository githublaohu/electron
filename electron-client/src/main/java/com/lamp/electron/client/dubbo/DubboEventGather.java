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
package com.lamp.electron.client.dubbo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.dubbo.config.spring.ServiceBean;
import org.apache.dubbo.config.spring.context.event.ServiceBeanExportedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.metadate.TypeMetadate;
import com.lamp.electron.client.AbstractGather;
import com.lamp.electron.client.ElectronProperties;

public class DubboEventGather extends AbstractGather implements EnvironmentAware, ApplicationListener<ApplicationEvent> {

	@Autowired
	private ElectronProperties electronProperties;
	
	private Environment environment;
	
	public DubboEventGather() {
		super("dubbo 2.7.4");
	}

	@PostConstruct
	private void init() {
		this.RPCType = "dubbo";
		if (Objects.nonNull(electronProperties)) {
			String port = environment.getProperty("dubbo.protocol.port");
			this.setPort(port != null ? Integer.valueOf(port):null);
			electronProperties.setApplicationName(
					electronProperties.getApplicationName() != null ? electronProperties.getApplicationName()
							: environment.getProperty("dubbo.application.name"));
			this.setElectronProperties(electronProperties);
			this.setProtocolEnum(ProtocolEnum.DUBBO);
		}
		this.verification();
	}
	
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if ( starUp.get() == 1 && event instanceof ServiceBeanExportedEvent) {
			ServiceBeanExportedEvent serviceBeanExportedEvent = (ServiceBeanExportedEvent)event;
			ServiceBean<?> serviceBean = serviceBeanExportedEvent.getServiceBean();
	        Class<?> ref = serviceBean.getInterfaceClass();
	        String basePath = "/" + electronProperties.getApplicationName() + "/" + ref.getSimpleName();
	        for (Method method : ref.getMethods()) {
	            this.registerInterface(basePath + "/" + method.getName(), createTypeMetadate(method),serviceBean.toUrl().toString(),serviceBean.getVersion(),method);
	        }
		}else if (event instanceof ApplicationStartedEvent) {
			
			this.register();
		}
	}

	private List<TypeMetadate>  createTypeMetadate(Method method){
		Parameter[] parameters = method.getParameters();
		if(Objects.isNull(parameters) || parameters.length == 0) {
			return null;
		}
		
		
		List<TypeMetadate> typeMetadateTypeList = new ArrayList<>(parameters.length);
		for(Parameter parameter:  parameters) {
			TypeMetadate typeMetadate = new TypeMetadate();
			typeMetadate.setIndex(typeMetadateTypeList.size());
			typeMetadate.setName(typeMetadate.getName());
			typeMetadate.setType(parameter.getType().getName());
		}
		return typeMetadateTypeList;
	}
	
}
