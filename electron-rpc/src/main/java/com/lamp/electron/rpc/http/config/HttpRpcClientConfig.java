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
package com.lamp.electron.rpc.http.config;

import com.lamp.electron.rpc.api.RpcConfig;

public class HttpRpcClientConfig extends RpcConfig {
	
	private int ioThreadNum = BASE_NUM*2;
	
	private int workThreadNum = CPU_NUM;

	public int getIoThreadNum() {
		return ioThreadNum;
	}

	public void setIoThreadNum(int ioThreadNum) {
		this.ioThreadNum = ioThreadNum;
	}

	public int getWorkThreadNum() {
		return workThreadNum;
	}

	public void setWorkThreadNum(int workThreadNum) {
		this.workThreadNum = workThreadNum;
	}
	
}