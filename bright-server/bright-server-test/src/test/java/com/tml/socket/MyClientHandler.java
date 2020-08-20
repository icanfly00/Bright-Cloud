package com.tml.socket;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description 客户端Handler
 * @Author TuMingLong
 * @Date 2020/7/22 11:01
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    private  static final Logger logger = LoggerFactory.getLogger(MyClientHandler.class);

    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private static ScheduledFuture<?> heartBeat;

    /**
     * 读取服务端返回来的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("开始接受服务端数据");
        ByteBuf m = (ByteBuf) msg;
        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            logger.info("服务端发送的数据为："+ DateUtil.format(new Date(currentTimeMillis),DatePattern.NORM_DATETIME_PATTERN));
        } finally {
            m.release();
        }
    }

    /**
     * 向服务端发送消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().remoteAddress()+"客户点活跃...");
        heartBeat=scheduler.scheduleWithFixedDelay(() ->{
            //TODO:向服务端写字符串
            logger.info("客户端连接服务端,开始发送数据.....");
            String string ="hello server！"+DateUtil.format(LocalDateTime.now(),DatePattern.CHINESE_DATE_TIME_PATTERN);
            System.out.println("发送数据为："+string);
            ByteBuf buf=ctx.alloc().buffer(4);
            buf.writeBytes(string.getBytes());
            ctx.writeAndFlush(buf);
            logger.info("发送完毕...");
        },1,5,TimeUnit.SECONDS);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端完成请求....");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("客户端异常："+cause.getMessage(),cause);
    }
}
