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
package com.lamp.electron.core.ability;

import java.util.Objects;

import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbilityInvoker implements Invoker {

	private Invoker asnyAbility;

	private ExecuteAbility nextAbility;

	private ExecuteAbility postAbility;

	private ExecuteAbility errorAbility;

	// 防止异常死循环
	private boolean isErron = false;

	private boolean isPostposition = false;

	public AbilityInvoker(AbilityInvoker abilityInvoker) {
		this.nextAbility = abilityInvoker.nextAbility;
		this.postAbility = abilityInvoker.postAbility;
		this.errorAbility = abilityInvoker.errorAbility;
	}

	public AbilityInvoker(ExecuteAbility nextAbility, ExecuteAbility postAbility, ExecuteAbility errorAbility) {
		this.nextAbility = nextAbility;
		this.postAbility = postAbility;
		this.errorAbility = errorAbility;
	}

	/**
	 * 需要画一张走向图
	 * 
	 * @param electronRequest
	 * @param electronResponse
	 */
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		if (Objects.nonNull(asnyAbility)) {
			electronResponse = asnyAbility.run(electronRequest, electronResponse, this);
			asnyAbility = null;
		}
		ExecuteAbility currentAbility;
		if (Objects.nonNull(electronResponse) && Objects.nonNull(electronResponse.throwable())) {
			currentAbility = errorAbility;
			isErron = true;
		} else {
			currentAbility = nextAbility;
		}
		ElectronResponse newElectronResponse = null;
		// 后置处理不允许返回 electronResponse, 可以递归，也就for(;;),尝试下goto
		for (;;) {
			try {
				newElectronResponse = currentAbility.run(electronRequest, electronResponse, this);
				// TODO 需要判断response是否存在异常
				
			} catch (Throwable e) {
				if (isErron == false) {
					// 这里生成一个异常对象 
					electronResponse = electronRequest.electronResponse(HttpResponseStatus.OK, null, null,e);
					currentAbility = nextAbility = errorAbility;
					isErron = true;
					log.error(e.getMessage(),e);
					continue;
				} else {
					// TODO 返回一定的结果,报警
					electronRequest.getAgreementResponse().reply(electronResponse);
					break;
				}

			}
			currentAbility = currentAbility.getNextAbility();
			// 如果链路为空，那么链路已经执行完了。执行完成只能使用 electronResponse
			if (Objects.isNull(currentAbility)) {
				electronRequest.getAgreementResponse().reply(electronResponse);
				break;
				/*
				 * if (!isPostposition) { isPostposition = true; currentAbility = nextAbility =
				 * postAbility; } else {
				 * 
				 * }
				 */
			}
			// 为null，表示继续执行动作 后置动作不能返回electronResponse
			// post 不能处理重置与异常形影
			if (Objects.isNull(newElectronResponse) || isPostposition) {
				continue;
			}

			// 进行重置
			if (ElectronResponse.RETRY_RESPONSE == newElectronResponse) {

			}

			// 异步就不执行了链路了，如果异步操作是异常，怎么办
			if (ElectronResponse.ANSY_RESPONSE == newElectronResponse) {
				nextAbility = currentAbility;
				break;
			}
			electronResponse = newElectronResponse;
			// newElectronResponse = null; 下个循环就会重新设值
			isPostposition = true;
			// 如果其他链路返回response，可以进入post
			currentAbility = nextAbility = postAbility;
		}
		return null;
	}

	public void asnyInvoker(Invoker invoker) {
		this.asnyAbility = invoker;
	}

}
