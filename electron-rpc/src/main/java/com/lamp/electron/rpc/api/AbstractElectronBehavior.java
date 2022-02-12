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
package com.lamp.electron.rpc.api;

import java.util.List;
import java.util.Map;

import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.invoker.ElectronBehavior;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.base.common.register.data.NetworkAddress;

import io.netty.buffer.ByteBuf;

public abstract class AbstractElectronBehavior implements ElectronBehavior {

	protected ByteBuf content;

	protected boolean isPerceptionContent = false;

	private Throwable throwable;
	
	private LongRangeWrapper longRangeWrapper;
	
	private List<NetworkAddress> networkAddressList;
	
	private String userKey;
	
	private Map<String,String> userInfo;
	
	private long requestBeginTime = System.currentTimeMillis();
	
	protected NetworkAddress networkAddress;
	
	
	
	public AbstractElectronBehavior() {}
	
	public  AbstractElectronBehavior(Throwable throwable) {
		this.throwable = throwable;
	}

	public void setLongRangeWrapper(LongRangeWrapper longRangeWrapper) {
		this.longRangeWrapper = longRangeWrapper;
		this.networkAddressList = longRangeWrapper.getNetworkAddress();
	}
	
	public LongRangeWrapper getLongRangeWrapper() {
		return longRangeWrapper;
	}
	
	public Throwable throwable() {
		return throwable;
	}

	protected void perception() {

	}

	public void content(ByteBuf byteBuf) {
		this.content = byteBuf;
		this.isPerceptionContent = true;
		this.perception();
	}

	public ByteBuf content() {
		return this.content;
	}

	public void setNetworkAddress( NetworkAddress networkAddress) {
		this.networkAddress = networkAddress;
	}
	
	public List<NetworkAddress> getNetworkAddressList(){
		return networkAddressList;
	}
	
	public void setNetworkAddressList(List<NetworkAddress> networkAddressList) {
		this.networkAddressList = networkAddressList;
	}
	
	public long requestBeginTime() {
		return requestBeginTime;
	}
	
	public void assignment(ElectronBehavior electronBehavior) {
	}
	
	public boolean setUserInfo(DataSpot dataSpot, String key, String value) {
		if(DataSpot.USER_KEY == dataSpot) {
			this.userKey = value;
			return true;
		}else if(DataSpot.USER_INFO == dataSpot) {
			return true;
		}
		return false;
	}
	
	public String getUserInfo(DataSpot dataSpot, String key) {
		if(DataSpot.USER_KEY == dataSpot) {
			return this.userKey;
		}else if(DataSpot.USER_INFO == dataSpot) {
			return userInfo.get(key);
		}
		return null;
	}
}
