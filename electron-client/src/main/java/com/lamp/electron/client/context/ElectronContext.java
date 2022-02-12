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
package com.lamp.electron.client.context;

public class ElectronContext {

    private static final ThreadLocal<ElectronContext> LOCAL = new ThreadLocal<ElectronContext>() {
        protected ElectronContext initialValue() {
            return new ElectronContext();
        }
    };

    public static ElectronContext getElectronContext() {
        return LOCAL.get();
    }

    private Object userInfo;

    @SuppressWarnings("unchecked")
    public <T> T getUserInfo() {
        return (T)userInfo;
    }

    public void setUserInfo(Object userInfo) {
        this.userInfo = userInfo;
    }
	
}
