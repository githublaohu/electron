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

import com.lamp.electron.base.common.enums.ProtocolEnum;

import lombok.Data;

/**
 * 如何解决结果集与模板业务问题
 * @author laohu
 *
 */
@Data
public class TrafficDetails {

	private Long id;

	private Long applicationId;

	private String applicationEnglishName;

	private String path;

	private String clientIP;

	private String upperReachesIP;

	private ProtocolEnum upperReachesProtocol;

	private String electronIP;

	private String electronPort;

	private String containerName;

	private String downstreamIP;

	private String downstreamPort;

	private ProtocolEnum downstreamProtocol;

	private String requestTime;

	private Integer requestFlowLength;

	private String requestFlow;

	private String responseTime;

	private Integer responseFlowLength;

	private String responseFlow;

}
