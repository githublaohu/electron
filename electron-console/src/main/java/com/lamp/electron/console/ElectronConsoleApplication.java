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
package com.lamp.electron.console;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.lamp.electron.console.entity.user.UserInfo;
import com.lamp.electron.console.security.SessionFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * $GatewayAnnotationScanner
 *
 * @author laohu
 * @since 2019年9月30日 下午2:24:47
 */

@Slf4j
@EnableAsync
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class ElectronConsoleApplication {

	/**
	 * 1. 一套测试数据
	 * 2. 
	 * @param args
	 */
    public static void main(String[] args) {
    	String uiSaltPassword = DigestUtils.md5Hex("4647d4e3c4b68a73eb492f14e0b2f1" + "1234567");
        try {
            SpringApplication.run(ElectronConsoleApplication.class, args);
            UserInfo userInfo = new UserInfo();
            userInfo.setUiId(1L);
            userInfo.setUiNickname("testestettt");
            SessionFactory.getInstance().setCache("lamp de23af25-ff12-4890-aaa8-2342816c20b3", userInfo);
            log.error("ElectronConsoleApplication 启动成功");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

}
