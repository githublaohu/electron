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
package com.lamp.electron.base.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class IncrementThreadFactory  implements ThreadFactory{

	private AtomicInteger threadIndex = new AtomicInteger(0);
	
	private String threadName;
	
	public IncrementThreadFactory(String threadName) {
		this.threadName = threadName;
	}
	
	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, threadName + this.threadIndex.incrementAndGet());
	}

}
