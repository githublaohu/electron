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
package com.lamp.electron.core.ability.function.statistics;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import com.alibaba.csp.sentinel.slots.statistic.base.LeapArray;
import com.alibaba.csp.sentinel.slots.statistic.data.MetricBucket;
import com.alibaba.csp.sentinel.slots.statistic.metric.ArrayMetric;
import com.alibaba.csp.sentinel.slots.statistic.metric.BucketLeapArray;
import com.alibaba.fastjson.JSON;
import com.lamp.electron.base.common.constant.ElectronConstant;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;
import com.lamp.electron.base.common.register.data.StatisticsDTO;
import com.lamp.electron.core.container.ContainerTiming;
import com.lamp.electron.rpc.message.DefaultMQProducerFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * <li>传入MQ对象</li>
 * <li>定时对象</li>
 * <li>使用哨兵，继承MetricBucket，加入其他字段，重写BucketLeapArray</li>
 * <li>限流也需要这个对象</li>
 * 
 * @author laohu
 *
 */
@Slf4j
public class StatisticsCentre implements ContainerTiming {

	private Map<OrganizationTypeEnum, Map<String, StatisticsDTO>> statisticsMap = new HashMap<>();

	{
		statisticsMap.put(OrganizationTypeEnum.APPLICATION, new ConcurrentHashMap<>());
		statisticsMap.put(OrganizationTypeEnum.INTERFACE, new ConcurrentHashMap<>());
		// 下面用于限流
		statisticsMap.put(OrganizationTypeEnum.INTERFACE_EXAMPLE, new ConcurrentHashMap<>());
		statisticsMap.put(OrganizationTypeEnum.EXAMPLE, new ConcurrentHashMap<>());
	}
	
	@Resource
	private DefaultMQProducerFactory defaultMQProducerFactory;

	public ArrayMetric createStatisticsObject(OrganizationTypeEnum organizationTypeEnum, String uniqueName) {

		Map<String, StatisticsDTO> statisticsDTOMap = statisticsMap.get(organizationTypeEnum);
		if (Objects.isNull(statisticsDTOMap)) {
			return null;
		}
		StatisticsDTO statisticsDTO = statisticsDTOMap.get(uniqueName);
		if (Objects.isNull(statisticsDTO)) {
			statisticsDTO = statisticsDTOMap.computeIfAbsent(uniqueName, key -> {
				StatisticsDTO newStatisticsDTO = new StatisticsDTO();
				newStatisticsDTO.setOrganizationEnglistName(uniqueName);
				newStatisticsDTO.setOrganizationTypeEnum(organizationTypeEnum);
				newStatisticsDTO.setChronoUnit(ChronoUnit.SECONDS);
				LeapArray<MetricBucket> leapArray = new BucketLeapArray(60, 60 * 1000);
				ArrayMetric arrayMetric = new ArrayMetric(leapArray);
				newStatisticsDTO.setLeapArray(leapArray);
				newStatisticsDTO.setArrayMetric(arrayMetric);
				newStatisticsDTO.setStartTime(getSeconds());
				return newStatisticsDTO;
			});
		}
		return statisticsDTO.getArrayMetric();
	}

	public long getSeconds() {
		long timeMillis = System.currentTimeMillis();
		return timeMillis - timeMillis % 1000;
	}

	@Override
	public long delay() {
		return 0;
	}

	@Override
	public void schedule() {
		long current = getSeconds();
		Map<String, StatisticsDTO> appLicationDTO = statisticsMap.get(OrganizationTypeEnum.APPLICATION);
		Map<String, StatisticsDTO> interaceDTO = statisticsMap.get(OrganizationTypeEnum.INTERFACE);

		for (StatisticsDTO dto : appLicationDTO.values()) {
			dto.rangeWindowValue(current);
		}

		for (StatisticsDTO dto : interaceDTO.values()) {
			dto.rangeWindowValue(current);
		}
		// 发送
		try {
			Message message = new Message(ElectronConstant.MESSAGE_TOPIC_STATISTIC,JSON.toJSONBytes(appLicationDTO.values()));
			defaultMQProducerFactory.getDefaultMQProducer().sendOneway(message);
			message = new Message(ElectronConstant.MESSAGE_TOPIC_STATISTIC,JSON.toJSONBytes(interaceDTO.values()));
			defaultMQProducerFactory.getDefaultMQProducer().sendOneway(message);
			log.error("统计数据发送成功");
		} catch (MQClientException | RemotingException | InterruptedException e) {
			log.error(e.getMessage(),e);
		}
	}

}
