package com.tml.gateway;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * @Description 用来计算经过的时间（精确到纳秒）
 * @Author TuMingLong
 * @Date 2020/7/29 9:29
 */
public class StopWatchTest {

    public static void main(String[] args) {
        Stopwatch stopwatch=Stopwatch.createStarted();

        try {
            TimeUnit.SECONDS.sleep(2);
            // 以秒打印从计时开始至现在的所用时间,向下取整
            // 用特定的格式返回这个stopwatch经过的时间
            System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));
            // 停止计时
            stopwatch.stop();
            System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));
            // 再次计时
            stopwatch.start();
            TimeUnit.MILLISECONDS.sleep(100);
            System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));
            // 重置并开始
            stopwatch.reset().start();
            TimeUnit.MILLISECONDS.sleep(1030);
            // 检查是否运行
            System.out.println(stopwatch.isRunning());
            long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            System.out.println(millis);
            // 打印
            System.out.println(stopwatch.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
