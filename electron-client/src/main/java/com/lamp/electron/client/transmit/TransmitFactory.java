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
package com.lamp.electron.client.transmit;


import java.util.ArrayList;
import java.util.List;

import com.lamp.electron.client.dubbo.DubboDransmission;

public class TransmitFactory implements Dransmission {

	private static final TransmitFactory INSTANCE = new TransmitFactory();
	
	private static final List<Dransmission> dransmissionList = new ArrayList<>();

	static {
		try {
			Class.forName("org.apache.dubbo.config.Constants");
			dransmissionList.add(new DubboDransmission());
		} catch (Exception e) {
			try {
				Class.forName("com.alibaba.dubbo.config.ServiceConfig");
				dransmissionList.add(new DubboDransmission());
			} catch (Exception e1) {

			}
		}
	}
	
	public static final TransmitFactory getInstance() {
		return INSTANCE;
	}

	private TransmitFactory() {}
	
	
	@Override
	public void dransmission(String key, String data) {
		for (Dransmission dransmission : dransmissionList) {
			dransmission.dransmission(key, data);
		}

	}

	@Override
	public String receive(String key) {
		return null;
	}

}
