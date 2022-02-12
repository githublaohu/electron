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
package com.lamp.electron.rpc.message;

import java.util.Objects;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.RequestCallback;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import com.lamp.electron.base.common.ability.MessageMiddlewareRPC;
import com.lamp.electron.base.common.ability.MessageMiddlewareRPC.MessageMiddlewareSendType;
import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.rpc.api.AbstractRpcBase;
import com.lamp.electron.rpc.api.RpcHandle;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageMiddlewareClient extends AbstractRpcBase implements Invoker {

	private DefaultMQProducerFactory defaultMQProducerFactory;

	public MessageMiddlewareClient(RpcHandle rpcHandle, DefaultMQProducerFactory defaultMQProducerFactory,
			Perception<RpcRequestConfig> perception) {
		super(perception, rpcHandle);
		this.defaultMQProducerFactory = defaultMQProducerFactory;
	}

	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {
		DefaultMQProducer producer = defaultMQProducerFactory.createDefaultMQProducer();
		if (Objects.isNull(producer)) {

		}
		MessageMiddlewareRPC messageMiddlewareRPC = getRequestConfig();
		try {
			Message message = new Message(messageMiddlewareRPC.getTopic(), electronRequest.contentByte());
			if (MessageMiddlewareSendType.ONEWAY == messageMiddlewareRPC.getSendType()) {
				producer.sendOneway(message);
				return electronRequest.electronResponse(HttpResponseStatus.OK,null, null, null);
			}
			if (MessageMiddlewareSendType.ASYNC == messageMiddlewareRPC.getSendType()) {

				producer.send(message, new SendCallback() {

					@Override
					public void onSuccess(SendResult sendResult) {
						MessageMiddlewareClient.this.callback(electronRequest,
								electronRequest.electronResponse(HttpResponseStatus.OK,null, null, null), invoker);
					}

					@Override
					public void onException(Throwable e) {
						MessageMiddlewareClient.this.callback(electronRequest,
								electronRequest.electronResponse(HttpResponseStatus.NOT_IMPLEMENTED,null, null, null),
								invoker);
						log.error(e.getMessage(), e);
					}
				}, messageMiddlewareRPC.getTimeout());

			} else if (MessageMiddlewareSendType.RUQUEST == messageMiddlewareRPC.getSendType()) {

				producer.request(message, new RequestCallback() {

					@Override
					public void onSuccess(Message message) {

						MessageMiddlewareClient.this.callback(electronRequest,
								electronRequest.electronResponse(HttpResponseStatus.OK, null, message.getBody(),null), invoker);
					}

					@Override
					public void onException(Throwable e) {

						MessageMiddlewareClient.this.callback(electronRequest,
								electronRequest.electronResponse(HttpResponseStatus.NOT_IMPLEMENTED,null, null, null),
								invoker);
						log.error(e.getMessage(), e);
					}
				}, messageMiddlewareRPC.getTimeout());

			}
		} catch (MQClientException | RemotingException | InterruptedException | MQBrokerException e) {
			log.error(e.getMessage(), e);
			return electronRequest.electronResponse(HttpResponseStatus.OK,null, null, null);
		}
		return ElectronResponse.ANSY_RESPONSE;
	}

	public void send(byte[] content, boolean isOneway) {
		DefaultMQProducer producer = defaultMQProducerFactory.createDefaultMQProducer();
		if (Objects.isNull(producer)) {
			log.error("producer create fail factrory is {}", defaultMQProducerFactory.toString());
			return;
		}
		MessageMiddlewareRPC messageMiddlewareRPC = getRequestConfig();
		try {
			Message message = new Message(messageMiddlewareRPC.getTopic(), content);
			if (isOneway) {
				producer.sendOneway(message);
			}
			if (isOneway) {
				producer.send(message, new SendCallback() {

					@Override
					public void onSuccess(SendResult sendResult) {

					}

					@Override
					public void onException(Throwable e) {
						log.error(e.getMessage(), e);
					}
				}, messageMiddlewareRPC.getTimeout());

			}
		} catch (MQClientException | RemotingException | InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	protected void init() {

	}

}
