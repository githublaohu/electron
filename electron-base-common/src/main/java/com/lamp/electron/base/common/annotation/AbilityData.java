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
package com.lamp.electron.base.common.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.lamp.electron.base.common.enums.AbilityScope;
import com.lamp.electron.base.common.enums.OrganizationTypeEnum;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface AbilityData {
	
	/**
	 * 动作作用域
	 */
	AbilityScope[] abilityScope();

	/**
	 * 动作名
	 */
	String name() default "";
	
	
	
	/**
	 * 中文名
	 */
    String chinaName();

	/**
	 * 
	 */
	String effect() default "";


	/**
	 * 同一个维度是否多绑定
	 */
	boolean manyBind() default false ;

	/**
	 * 是否是全局策略
	 */
	boolean overallSituation() default false;

	/**
	 * 动作链路只执行一个
	 */
	boolean executeMany() default false;
	
	/**
	 * 重置到某个动作
	 */
	Class<?> retrySpot() default Object.class;
	
	/**
	 * 绑定维度
	 */
	OrganizationTypeEnum[] abilityBindRelation() default OrganizationTypeEnum.APPLICATION;

	String childDataName() default "";
	
	/**
	 * 说明
	 */
	String explain() default "";	

}
