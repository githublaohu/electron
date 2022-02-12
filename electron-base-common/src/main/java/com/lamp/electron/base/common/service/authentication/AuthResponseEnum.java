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
package com.lamp.electron.base.common.service.authentication;

public enum AuthResponseEnum {

    AUTH_RESULT_FAIL,
    
    AUTH_RESULT_FAIL_NET_ERROR,
    
    AUTH_RESULT_FAIL_NOT_SERVER,
   
    AUTH_RESULT_USER_FAIL,
   
    AUTH_RESULT_JURISDICTION_FAIL,
   
    AUTH_RESULT_SUCCESS;
}
