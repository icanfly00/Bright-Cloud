package com.tml.socket;

import com.tml.common.utils.HexConvert;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description
 * @Author TuMingLong
 * @Date 2020/7/22 10:51
 */
public class MySensorServerHandler extends ChannelInboundHandlerAdapter {
    private  static final Logger logger = LoggerFactory.getLogger(MyServer.class);

    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private static ScheduledExecutorService service2 = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

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
        String returnStr= HexConvert.byte2HexStr(bytes);
        logger.info("客户端发送的数据为："+returnStr);
        //TODO: 发送指令
        AtomicReference<String> sendStr= new AtomicReference<>("");
        AtomicReference<ChannelFuture> f = new AtomicReference<>();
        if("FF20000001".equals(returnStr)){
            //TODO: 开灯 任务初始延迟5秒，任务执行间隔为5分
            service.scheduleWithFixedDelay(() -> {
                logger.info("开命令");
                //TODO: 开命令
                sendStr.set("FE 06 80 01 A3 CA 01 63 C9");
                logger.info("服务端发送的数据为："+sendStr);
                f.set(ctx.writeAndFlush(HexConvert.hexStr2Bytes(sendStr.get())));
            }, 5, 5*60, TimeUnit.SECONDS);

            //TODO: 关灯 任务初始延迟245秒，任务执行间隔为9分
            service2.scheduleAtFixedRate(() -> {
                logger.info("关命令");
                //TODO: 关命令
                sendStr.set("FE 06 80 01 A3 CA 00 A2 09");
                logger.info("服务端发送的数据为："+sendStr);
                f.set(ctx.writeAndFlush(HexConvert.hexStr2Bytes(sendStr.get())));
            }, 4*60+5, 9*60, TimeUnit.SECONDS);

        }
        if(f.get()!=null){
            f.get().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    assert f == future;
                    logger.info("监听中");
                    //ctx.close();
                }
            });
        }
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
