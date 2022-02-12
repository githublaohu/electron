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
package com.lamp.electron.core.container.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import com.alibaba.fastjson.JSON;
import com.lamp.electron.base.common.constant.ElectronConstant;
import com.lamp.electron.base.common.register.data.TrafficDetails;
import com.lamp.electron.core.container.ContainerTiming;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置一个注册中心，然后直接一次发送</br>
 * 
 * @author laohu
 *
 */
@Slf4j
public class TrafficDetailsBean implements ContainerTiming {

	private DefaultMQProducer defaultMQProducer;

	private List<TrafficDetailsThreadLocal> trafficDetailsThreadLocalList = new ArrayList<>();

	private final ThreadLocal<TrafficDetailsThreadLocal> THREAD_LOCAL = new ThreadLocal<TrafficDetailsThreadLocal>() {

		protected TrafficDetailsThreadLocal initialValue() {
			TrafficDetailsThreadLocal trafficDetailsThreadLocal = new TrafficDetailsThreadLocal();
			trafficDetailsThreadLocalList.add(trafficDetailsThreadLocal);
			return trafficDetailsThreadLocal;
		}
	};

	final static class TrafficDetailsThreadLocal {
		volatile List<TrafficDetails> trafficDetailsList = new ArrayList<>();
		volatile List<TrafficDetails> spareTrafficDetailsList = new ArrayList<>();
		long time = System.currentTimeMillis();
	}

	public void setTrafficDetails(TrafficDetails trafficDetails, Object object, boolean immediately) {
		if (immediately) {
			Message message = new Message(ElectronConstant.MEESGAE_TOPIC_TRAFFIC_DETAILS_TOPIC,JSON.toJSONBytes(trafficDetails));
			try {
				defaultMQProducer.sendOneway(message);
			} catch (MQClientException | RemotingException | InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		} else {
			TrafficDetailsThreadLocal trafficDetailsThreadLocal = THREAD_LOCAL.get();
			trafficDetailsThreadLocal.trafficDetailsList.add(trafficDetails);
		}
	}

	@Override
	public long delay() {
		return 1000;
	}

	@Override
	public void schedule() {
		for (TrafficDetailsThreadLocal detailsThreadLocal : trafficDetailsThreadLocalList) {
			List<TrafficDetails> spareTrafficDetailsList = detailsThreadLocal.spareTrafficDetailsList;
			detailsThreadLocal.spareTrafficDetailsList = detailsThreadLocal.trafficDetailsList;
			detailsThreadLocal.trafficDetailsList = spareTrafficDetailsList;
		}
		
		for (TrafficDetailsThreadLocal detailsThreadLocal : trafficDetailsThreadLocalList) {
			Message message = new Message(ElectronConstant.MEESGAE_TOPIC_TRAFFIC_DETAILS_BATCH_TOPIC,JSON.toJSONBytes(detailsThreadLocal.spareTrafficDetailsList));
			try {
				defaultMQProducer.sendOneway(message);
			} catch (MQClientException | RemotingException | InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
