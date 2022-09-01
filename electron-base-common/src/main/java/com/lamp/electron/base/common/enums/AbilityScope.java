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
package com.lamp.electron.base.common.enums;

/**
 * @author jelly
 */
public enum AbilityScope {
	
	COLLECT("收集",""),
	DISCERN("识别",""),
	CHOICE("路由",""),
	SECURITY("安全",""),
	RPC("RPC配置",""),
	ERROR("异常处理",""),
	INVOKE("invoke处理",""),
	SECURITY_SURROUND("","",false),
	SURROUND("","",false),
	CONFIG("配置",""),
	REGISTER("注册","可以注册实例信息，静态资源"),
	INVOK("执行","执行调用"),
	RESULT("结果处理",""),
	;
	
	String label;
	
	String explain;
	
	private boolean tree = true;
	
	AbilityScope(String label, String explain ){
		this.label = label;
		this.explain = explain;
	}
	
	AbilityScope(String label, String explain, boolean tree ){
		this.label = label;
		this.explain = explain;
		this.tree = tree;
	}
	
	public boolean isTree() {
		return this.tree;
	}

	public String getLabel() {
		return label;
	}

	public String getExplain() {
		return explain;
	}
	
	
}
