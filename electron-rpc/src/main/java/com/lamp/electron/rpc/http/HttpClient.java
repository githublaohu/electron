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
package com.lamp.electron.rpc.http;

import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.rpc.api.AbstractRpcBase;
import com.lamp.electron.rpc.api.RpcHandle;
import com.lamp.electron.rpc.api.client.RpcClient;

/**
 * 1. 要支持重定向
 * 2. 超时
 * @author laohu
 *
 */
public class HttpClient extends AbstractRpcBase implements RpcClient {

	public HttpClient(Perception<RpcRequestConfig> perception, RpcHandle rpcHandle) {
		super(perception, rpcHandle);
	}

	@Override
	public ProtocolEnum rpcType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(ElectronRequest electronRequest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

/*	private HttpRpcClientConfig httpRpcClientConfig;

	private EventLoopGroup workerGroup;

	private DefaultEventExecutorGroup defaultEventExecutorGroup;

	private Bootstrap b = new Bootstrap();

	public HttpClient(RpcHandle rpcHandle, HttpRpcClientConfig httpRpcClientConfig) {
		super(rpcHandle,httpRpcClientConfig);
		this.httpRpcClientConfig = httpRpcClientConfig;
	}

	@Override
	public ProtocolEnum rpcType() {
		return ProtocolEnum.HTTP;
	}

	@Override
	public void send(ElectronRequest electronRequest) {

	}

	protected void init() {
		if (!useEpoll()) {
			this.workerGroup = new EpollEventLoopGroup(httpRpcClientConfig.getIoThreadNum(),
					new IncrementThreadFactory("netty-http-server-ehandler-"));
		} else {
			this.workerGroup = new NioEventLoopGroup(httpRpcClientConfig.getIoThreadNum(),
					new IncrementThreadFactory("netty-http-server-selector-"));
		}
		b.group(workerGroup);
		b.channel(NioSocketChannel.class);
		b.option(ChannelOption.SO_KEEPALIVE, true);

		this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(httpRpcClientConfig.getWorkThreadNum(),
				new IncrementThreadFactory("netty-http-server-handler-"));
		b.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(defaultEventExecutorGroup, new LightIdleStateHandler(), new HttpResponseDecoder(),
						new HttpRequestEncoder(), new HttpClientHandler());

			}
		});
	}

	public Channel getChannle(InetSocketAddress inetSocketAddress) throws InterruptedException {
		return b.connect(inetSocketAddress).sync().channel();
	}

	public void write(AsynReturn asynReturn, InetSocketAddress inetSocketAddress) {
		Channel channel = b.connect(inetSocketAddress).channel();
		channel.id();
		channel.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					channelIdToAsynReturn.put(future.channel().id(), asynReturn);
					future.channel().writeAndFlush(asynReturn.getFullHttpRequest());
				} else {
					// 异常
				}
			}
		});
	}

	class HttpClientHandler extends ChannelInboundHandlerAdapter {

		private DefaultHttpResponse defaultHttpResponse;

		private ByteBuf connect;

		private AsynReturn asynReturn;

		private Throwable throwable;

		@Override
		public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
			asynReturn = HttpClient.this.channelIdToAsynReturn.remove(ctx.pipeline().channel().id());
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

			if (msg instanceof DecoderResultProvider) {
				DecoderResultProvider decoderResultProvider = (DecoderResultProvider) msg;
				DecoderResult decoderResult = decoderResultProvider.decoderResult();
				if (Objects.nonNull(decoderResult) && !decoderResult.isSuccess()) {
					// 异常
					throwable = decoderResult.cause();
				}
			}
			if (msg instanceof DefaultHttpResponse) {
				defaultHttpResponse = (DefaultHttpResponse) msg;
				HttpHeaders headers = defaultHttpResponse.headers();
				Integer contentLength = headers.getInt(HttpHeaderNames.CONTENT_LENGTH);
				connect = Objects.isNull(contentLength) ? Unpooled.buffer(8192) : Unpooled.buffer(contentLength);

			}

			if (msg instanceof LastHttpContent) {
				LastHttpContent lastHttpContent = (LastHttpContent) msg;
				connect.writeBytes(lastHttpContent.content());
				returnHandle();
			}

			if (msg instanceof HttpContent) {
				HttpContent content = (HttpContent) msg;
				connect.writeBytes(content.content());
			}

		}

		private void returnHandle() {

		}

		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			ctx.fireUserEventTriggered(evt);
		}

	}

	class LightIdleStateHandler extends ChannelDuplexHandler {

		private IdleStateHandler idleStateHandler;

		private IdleStateHandler getIdleStateHandler(ChannelHandlerContext ctx) throws Exception {
			if (Objects.isNull(idleStateHandler)) {
				AsynReturn asynReturn = HttpClient.this.channelIdToAsynReturn.get(ctx.pipeline().channel().id());
				// 得到时间
				idleStateHandler = new IdleStateHandler(-1, -1, asynReturn.getRequestTimes());
				idleStateHandler.handlerAdded(ctx);
			}
			return idleStateHandler;
		}

		@Override
		public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
			// getIdleStateHandler(ctx).handlerAdded(ctx);
		}

		@Override
		public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
			// getIdleStateHandler(ctx).handlerAdded(ctx);
		}

		@Override
		public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
			// getIdleStateHandler(ctx).channelRegistered(ctx);
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			getIdleStateHandler(ctx).channelActive(ctx);
		}

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			getIdleStateHandler(ctx).channelInactive(ctx);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			getIdleStateHandler(ctx).channelRead(ctx, msg);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			getIdleStateHandler(ctx).channelReadComplete(ctx);
		}

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			getIdleStateHandler(ctx).write(ctx, msg, promise);
		}

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}*/

}
