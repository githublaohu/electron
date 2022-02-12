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

import java.nio.charset.Charset;
import java.util.Map;

import com.lamp.electron.base.common.enums.DataSpot;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.metadate.ClassificationEnum;

import io.netty.buffer.ByteBuf;

/**
 * <p>
 * 数据有object，list每个都要兼容
 * </p>
 * <p>
 * 数据类型基本有body，header
 * </p>
 * <p>
 * 协议之间如何简单的转换，写一个工具类，还是内部转换
 * </p>
 * @author laohu
 *
 */
public interface ElectronBehavior {

	public static final Charset CHARSET = Charset.forName("UTF-8");
	
	/**
	 * 请求协议
	 * @return
	 */
	public ProtocolEnum getProtocolEnum();

	/**
	 * 
	 * @param dataSpot
	 * @param key
	 * @return
	 */
	public String data(DataSpot dataSpot, String key);

	/**
	 * <p>
	 * 注意object与list
	 *  </p>
	 *  <p>
	 * 注意性能
	 *  </p>
	 *  先这样，优化data（DataSpot dataSpot, ClassificationEnum classificationEnum）方法，尽量替代本方法
	 * @param dataSpot
	 * @return
	 */
	public Map<String, String> data(DataSpot dataSpot);

	public Object data(DataSpot dataSpot, ClassificationEnum classificationEnum);

	public void setData(DataSpot dataSpot, String key, String value);

	/**
	 * 主要是http协议
	 * @return
	 */
	public default boolean isResource() {return false;}

	/**
	 * 这个不应该暴露
	 * @return
	 */
	public ByteBuf content();
	
	/**
	 * 应该直接异常
	 * @return
	 */
	public default byte[] contentByte() {return null;}
	
	public default String contentString() {return null;}
	

	/**
	 * 直接返回原始对象
	 * @return
	 */
	public Object original();
	
	/**
	 * 
	 * @param electronBehavior
	 */
	public void assignment(ElectronBehavior electronBehavior);
	

	public long requestBeginTime();
}
