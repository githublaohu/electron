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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lamp.electron.client.ElectronProperties;
import com.lamp.electron.client.spring.boot.autoconfigure.ElectronAutoProperties;

public class ElectronWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired
	private ElectronAutoProperties electronAutoProperties;
	
	
	 @Override
     public void addInterceptors(InterceptorRegistry registry) {
         try {
			registry.addInterceptor(new ElectonInterceptor((ElectronProperties)electronAutoProperties)).addPathPatterns("/**");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
     }
}
