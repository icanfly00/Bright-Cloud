package com.tml.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author TuMingLong
 * @Date 2020/7/22 10:51
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    private  static final Logger logger = LoggerFactory.getLogger(MyServer.class);

    /**
     * 通道活跃事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().remoteAddress()+"通道活跃....");
    }

    /**
     * 通道不活跃事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.error(ctx.channel().remoteAddress()+"通道不活跃....");
    }

    /**
     *  读取客户端传过来的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //TODO:业务处理类
        logger.info("开始业务处理....");
        byte[] bytes= (byte[]) msg;
        logger.info("客户端发送的数据为："+new String(bytes));
        final ByteBuf time = ctx.alloc().buffer(4);
        long currentTimeMillis=System.currentTimeMillis() / 1000L + 2208988800L;
        logger.info("服务端发送的的数据为："+currentTimeMillis);
        time.writeInt((int) currentTimeMillis);
        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                logger.info("监听中");
                //ctx.close();
            }
        });
    }

    /**
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("服务端完成请求");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //TODO:出现异常，关闭连
        logger.error("服务端出现异常："+cause.getMessage(),cause);
        ctx.close();
    }
}
