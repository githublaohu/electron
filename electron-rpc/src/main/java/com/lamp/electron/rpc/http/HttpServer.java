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

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lamp.electron.base.common.IncrementThreadFactory;
import com.lamp.electron.base.common.enums.ProtocolEnum;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.rpc.api.AbstractAgreementResponse;
import com.lamp.electron.rpc.api.AbstractRpcBase;
import com.lamp.electron.rpc.api.RpcConfig;
import com.lamp.electron.rpc.api.RpcHandle;
import com.lamp.electron.rpc.api.RpcServer;
import com.lamp.electron.rpc.http.api.HttpRequest;
import com.lamp.electron.rpc.http.config.HttpRpcServerConfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

public class HttpServer extends AbstractRpcBase implements RpcServer {

	private static final Logger log = LoggerFactory.getLogger(HttpServer.class);

	private ServerBootstrap serverBootstrap;

	private EventLoopGroup eventLoopGroupSelector;

	private EventLoopGroup eventLoopGroupBoss;

	private DefaultEventExecutorGroup defaultEventExecutorGroup;


	public HttpServer(RpcHandle rpcHandle, RpcConfig rpcConfig) {
		super(rpcHandle,rpcConfig);
	}

	@Override
	public ProtocolEnum rpcType() {
		return ProtocolEnum.HTTP;
	}

	@Override
	public void reply(ElectronResponse electronResponse) {

	}

	@Override
	protected void init() {
		HttpRpcServerConfig httpConfig = getRpcConfig();
		this.serverBootstrap = new ServerBootstrap();
		if (useEpoll()) {
			this.eventLoopGroupBoss = new EpollEventLoopGroup(httpConfig.getBossThreadNum(),
					new IncrementThreadFactory("netty-http-server-eboss-"));
			this.eventLoopGroupSelector = new EpollEventLoopGroup(httpConfig.getIoThreadNum(),
					new IncrementThreadFactory("netty-http-server-ehandler-"));
		} else {
			this.eventLoopGroupBoss = new NioEventLoopGroup(httpConfig.getBossThreadNum(),
					new IncrementThreadFactory("netty-http-server-boss-"));
			this.eventLoopGroupSelector = new NioEventLoopGroup(httpConfig.getIoThreadNum(),
					new IncrementThreadFactory("netty-http-server-selector-"));
		}

		this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(httpConfig.getWorkThreadNum(),
				new IncrementThreadFactory("netty-http-server-handler-"));

		this.serverBootstrap.group(this.eventLoopGroupBoss, this.eventLoopGroupSelector)
				.channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_REUSEADDR, true)
				.option(ChannelOption.SO_KEEPALIVE, false).childOption(ChannelOption.TCP_NODELAY, true)
				.localAddress(new InetSocketAddress(httpConfig.getPort()))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {

						ch.pipeline().addLast(defaultEventExecutorGroup, new HttpServerCodec(),
								new HttpObjectAggregator(32 * 1024 * 1024), new HttpServerExpectContinueHandler(),
								new NettyHttpServerHandler());
					}
				});

		try {
			ChannelFuture sync = this.serverBootstrap.bind().sync();
			InetSocketAddress addr = (InetSocketAddress) sync.channel().localAddress();
			log.info("http 服务启动成功，本地地址是 {}:{}", addr.getAddress(), addr.getPort());
		} catch (InterruptedException e1) {
			throw new RuntimeException("HTTP 服务启动失败 ", e1);
		}

	}

	@Override
	public void shutdown() {
		try {
			this.serverBootstrap.register();
			this.eventLoopGroupBoss.shutdownGracefully();
			this.eventLoopGroupSelector.shutdownGracefully();
			this.defaultEventExecutorGroup.shutdownGracefully();
		} catch (Exception e) {
			log.error("http 服务关闭失败 ", e);
		}
	}

	public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
			try {

				if (message instanceof FullHttpRequest) {
					AbstractAgreementResponse<ChannelHandlerContext> httpAgreementResponse = new HttpAgreementResponse();
					httpAgreementResponse.setAgreemoent(ctx);
					HttpServer.this.receive(new HttpRequest((FullHttpRequest)message,httpAgreementResponse));
				} else {
					boolean release = ReferenceCountUtil.release(message);
					if (!release) {
						log.error("资源释放失败，资源是 {}", message);
					}
				}
			} catch (Exception e) {
				boolean release = ReferenceCountUtil.release(message);
				if (!release) {
					log.error("资源释放失败，资源是 {}", message);
				}
				log.error(e.getMessage() ,e);
			}
		}

	}

}
