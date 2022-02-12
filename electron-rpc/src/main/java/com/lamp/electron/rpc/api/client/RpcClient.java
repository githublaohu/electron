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
package com.lamp.electron.rpc.api.client;

import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.rpc.api.RpcElement;


/**
 * 只负责执行，不负责。client的创建。需要一个rpcclient创建的点。而且每个的操作方式不一样。通过create与client不同
 * @author laohu
 *
 */
public interface RpcClient extends RpcElement{

	public void send(ElectronRequest electronRequest);
}
