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
package com.lamp.electron.base.common.register.data;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.csp.sentinel.slots.statistic.base.LeapArray;
import com.alibaba.csp.sentinel.slots.statistic.base.WindowWrap;
import com.alibaba.csp.sentinel.slots.statistic.data.MetricBucket;
import com.alibaba.csp.sentinel.slots.statistic.metric.ArrayMetric;
import com.alibaba.fastjson.annotation.JSONField;
import com.lamp.electron.base.common.basedata.RegisterBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatisticsDTO extends RegisterBase {

	@JSONField(serialize = false, deserialize = false)
	private ArrayMetric arrayMetric;

	private List<WindowWrap<MetricBucket>> metricBucketList = new ArrayList<>();

	@JSONField(serialize = false, deserialize = false)
	private LeapArray<MetricBucket> leapArray;

	private ChronoUnit chronoUnit;

	private long startTime;

	private long endTime;

	public void rangeWindowValue(long endTime) {
		startTime = this.endTime;
		metricBucketList.clear();
		long currentTime = startTime;
		for (;;) {
			WindowWrap<MetricBucket> metricBucket = leapArray.currentWindow(currentTime);
			if (metricBucket.value().pass() == 0) {
				continue;
			}
			metricBucketList.add(metricBucket);
			currentTime = currentTime + 1000;
			if (currentTime >= endTime) {
				break;
			}
		}
		this.endTime = endTime;
	}

}
