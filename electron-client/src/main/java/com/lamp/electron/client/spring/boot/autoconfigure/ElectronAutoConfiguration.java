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
package com.lamp.electron.client.spring.boot.autoconfigure;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lamp.electron.base.common.constant.ElectronConstant;
import com.lamp.electron.client.dubbo.DubboEventGather;
import com.lamp.electron.client.spring.SpringMVCGather;

@Configuration
@EnableConfigurationProperties(ElectronAutoProperties.class)
@ConditionalOnProperty(prefix = ElectronConstant.ELECTRON_NAME, name = { "registry","applicationName" })
public class ElectronAutoConfiguration {

	@Autowired
	private ElectronAutoProperties electronAutoProperties;

	@Bean
	@ConditionalOnProperty(prefix = "server", name = { "port" })
	@ConditionalOnClass(name = "org.springframework.boot.autoconfigure.web.ServerProperties")
	public SpringMVCGather springMVCClientManger() {
		return new SpringMVCGather();
	}

	@Bean
	@ConditionalOnClass(name = "org.apache.dubbo.config.Constants")
	public DubboEventGather DubboClientManger() {
		return new DubboEventGather();
	}

	@Bean
	@ConditionalOnProperty(prefix = "server", name = { "port" })
	@ConditionalOnClass(name = "org.springframework.boot.autoconfigure.web.ServerProperties")
	public Object downstreamInterceptor() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if(Objects.nonNull(electronAutoProperties.getUserInfoClass())) {
			Class<?> clazz = Class.forName("com.lamp.electron.client.spring.ElectronWebMvcConfigurer");
			return clazz.newInstance();
		}
		
		return new Object();
	}

}
