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

import java.util.List;
import java.util.Objects;

import com.lamp.electron.base.common.basedata.NodeBase;
import com.lamp.electron.base.common.metadate.TypeMetadate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InterfaceInfo extends NodeBase {

	private String url;

	private String path;

	private String[] httpMethodType;

	private String className;

	private String methodName;

	private List<TypeMetadate> parameter;

	private TypeMetadate returnObject;

	private String[] parameterNameArray;

	private Integer servierSign;

	public String[] getPrameterNameArray() {
		if (Objects.nonNull(parameter) && Objects.isNull(this.parameterNameArray)) {
			String[] parameterNameArray = new String[parameter.size()];
			for (int i = 0; i < parameter.size(); i++) {
				parameterNameArray[i] = parameter.get(i).getType();
			}
			this.parameterNameArray = parameterNameArray;
		}
		return this.parameterNameArray;
	}

	public String name() {
		return path;
	}

}
