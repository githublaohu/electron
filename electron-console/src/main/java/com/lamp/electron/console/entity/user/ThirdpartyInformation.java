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
package com.lamp.electron.console.entity.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ThirdpartyInformation  {

	private Long tiId;

	private Long uiId;

	private Long tiPlatformId;

	private String tiPlatformName;

	private String tiPlatformUserId;
	
	private Long tiSoftwareId;

	private String tiSoftwareName;

	private String tiSoftwareUserId;

	private String tiType;
	
	private String tiToken;

	private String tiLoginTime;

	private String tiForbidTime;

}
