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
package com.lamp.electron.base.common.invoker;

import java.util.Map;

import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.metadate.ClassificationEnum;

import io.netty.buffer.ByteBuf;

public class AsnyElectronResponse  implements ElectronResponse {

	public AsnyElectronResponse() {
		
	}

	@Override
	public String data(DataSpot type, String key) {
		return null;
	}

	@Override
	public void setData(DataSpot type, String key, String value) {
		
	}

	@Override
	public ByteBuf content() {
		return null;
	}

	@Override
	public Throwable throwable() {
		return null;
	}



	@Override
	public ProtocolEnum getProtocolEnum() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Map<String, String> data(DataSpot dataSpot) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object data(DataSpot dataSpot, ClassificationEnum classificationEnum) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isResource() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public Object original() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int statusCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void assignment(ElectronBehavior electronBehavior) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long requestBeginTime() {
		// TODO Auto-generated method stub
		return 0;
	}

}
