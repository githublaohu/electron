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
package com.lamp.electron.base.common.metadate;

import java.util.List;

import lombok.Data;

/**
 * @author laohu
 *
 */
@Data
public class TypeMetadate {

	/**
	 * 参数名
	 */
	private  String name;
	
	private int index;
	
	private String type;
	
	/**
	 * 参数类型分类
	 */
	private ClassificationEnum  classificationEnum;
	
	/**
	 * 
	 */
	private String typeFullName; 
	
	private String checkExpression;
	
	private List<CheckMetadate> checkList;
	
	private boolean paradigm;
	
	private List<TypeMetadate> paradigmTypeList;
	
	private List<TypeMetadate> attributeList;
	
	
}
