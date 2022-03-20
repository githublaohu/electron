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
package com.lamp.electron.client.spring;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.client.AbstractGather;
import com.lamp.electron.client.ElectronProperties;

public class SpringMVCGather extends AbstractGather
		implements ApplicationListener<ApplicationEvent>, ApplicationContextAware {


	ApplicationContext applicationContext;

	@Autowired
	private ServerProperties server;

	@Autowired
	private ElectronProperties electronProperties;
	
	private String contextPath;
	
	public SpringMVCGather() {
		super("spring boot web container");
		this.RPCType = "springmvc";
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	private void init() {
		if (Objects.nonNull(electronProperties)) {
			this.setPort(server != null ? server.getPort() : null);
			electronProperties.setApplicationName(
					electronProperties.getApplicationName() != null ? electronProperties.getApplicationName()
							: server.getServlet().getApplicationDisplayName());
			this.setElectronProperties(electronProperties);
			this.setProtocolEnum(server.getHttp2().isEnabled()?ProtocolEnum.HTTP2:ProtocolEnum.HTTP);
		}
		this.verification();
		contextPath = Objects.isNull(server.getServlet().getContextPath()) ? "" : server.getServlet().getContextPath();

	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
			if ((starUp.get() == 1)&&(event instanceof WebServerInitializedEvent || event instanceof ServletWebServerInitializedEvent)
					&& starUp.incrementAndGet() == 2) {
				gather();
			}
	}

	private void gather() {
		Map<String, RequestMappingHandlerMapping> allRequestMappings = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(applicationContext, RequestMappingHandlerMapping.class, true, false);

		for (RequestMappingHandlerMapping handlerMapping : allRequestMappings.values()) {
			Map<RequestMappingInfo, HandlerMethod> handlerMethods = ((RequestMappingHandlerMapping) handlerMapping)
					.getHandlerMethods();
			for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods
					.entrySet()) {
				RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
				Method method = requestMappingInfoHandlerMethodEntry.getValue().getMethod();
				Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
				for (String pattern : patterns) {
					if (StringUtils.isEmpty(pattern) || StringUtils.equals(pattern, "/")) {
						pattern = "/$" + electronProperties.getApplicationName();
					}
					if ("/error".equals(pattern)) {
						continue;
					}
					this.registerInterface(contextPath + pattern,getRequestType(requestMappingInfo.getMethodsCondition().getMethods()),method);
				}
			}
		}
		this.register();
	}
	
	private String[] getRequestType(Set<RequestMethod> methods){
	    String[] reques = new String[methods.size()];
	    int i = 0;
	    for(RequestMethod method : methods) {
	    	reques[i++] = method.name();
	    }
	    return reques;
	}

}
