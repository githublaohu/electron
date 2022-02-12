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
package com.lamp.electron.base.common.viewmodel;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE, METHOD,FIELD })
public @interface DataModeBehavior {

	String name() default "";
	
	BehaviorType behaviorType();
	
	String key() default "";
	
	String title();
	
	String type() default "";
	
	String defaultValue() default "";
	
	boolean isBehavior() default true;
	
	boolean isHide() default  false;
	
	boolean isDisabled() default false;
	
	boolean isUser() default true;
	
	String iff() default "";
	
	String ifValue()default "";
	
	String[] rules() default "";
	
	String valueExpression() default "";
	
	boolean isColumn() default false;
}
