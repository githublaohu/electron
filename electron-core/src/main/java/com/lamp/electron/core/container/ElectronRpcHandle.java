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
package com.lamp.electron.core.container;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.register.data.LongRangeWrapper;
import com.lamp.electron.core.ability.AbilityInvoker;
import com.lamp.electron.core.ability.AbilityInvokerManage;
import com.lamp.electron.rpc.api.RpcHandle;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class ElectronRpcHandle implements RpcHandle {

	private ThreadPoolExecutor threadPoolExecutor;

	private AbilityInvokerManage abilityInvokerManage;

	// 线程池
	public ElectronRpcHandle(ThreadPoolExecutor threadPoolExecutor, AbilityInvokerManage abilityInvokerManage) {
		this.threadPoolExecutor = threadPoolExecutor;
		this.abilityInvokerManage = abilityInvokerManage;
	}

	/**
	 * 服务端调用 不同的协议使用不同的解决方案，http有资源，条件。而dubbo与其他直接api
	 */
	@Override
	public void receive(ElectronRequest electronRequest) {
		// 这里应该调用恰的把
		threadPoolExecutor.execute(new Runnable() {

			@Override
			public void run() {
				// 寻找api
				try {
					AbilityInvoker abilityInvoker = abilityInvokerManage.getAbilityInvoker(electronRequest);
					if (Objects.nonNull(abilityInvoker)) {
						abilityInvoker.run(electronRequest, null, null);
					}else {
						
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});

	}
	
	@Override
	public void receive(ElectronRequest electronRequest,LongRangeWrapper longRangeWrapper) {
		// 这里应该调用恰的把
		threadPoolExecutor.execute(new Runnable() {

			@Override
			public void run() {
				// 寻找api
				try {
					AbilityInvoker abilityInvoker = abilityInvokerManage.interfaceAndAbility(electronRequest,longRangeWrapper);
					if (Objects.nonNull(abilityInvoker)) {
						abilityInvoker.run(electronRequest, null, null);
					}else {
						
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});

	}

	/**
	 * rpc client异步回调
	 */
	@Override
	public void callback(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				invoker.run(electronRequest, electronResponse, invoker);
			}
		});
	}

	@Override
	public void execute(Runnable command) {
		threadPoolExecutor.execute(command);
	}

}
