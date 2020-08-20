package com.tml.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @Description
 * @Author TuMingLong
 * @Date 2020/7/22 10:57
 */
public class MyClient {
    private  static final Logger logger = LoggerFactory.getLogger(MyClient.class);
    private String host;
    private int port;
    public MyClient(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }
    public void connect(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    logger.info("客户端触发连接......");
                    ch.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                    ch.pipeline().addLast(new MyClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = bootstrap.connect(host, port).sync();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
