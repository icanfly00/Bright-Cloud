package com.tml.java8;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Description com.tml.java8
 * @Author TuMingLong
 * @Date 2020/3/20 20:53
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        Map<String,Object> map=new HashMap<>();

       // create();
        create2();
    }

    public static void hireCar(){
        //任务1 订购航班
        CompletableFuture<String> orderAirplane=CompletableFuture.supplyAsync(()->{
            System.out.println("查询航班");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("订购航班");
            return "航班信息";
        });

        //任务2 订购酒店
        CompletableFuture<String> orderHotel=CompletableFuture.supplyAsync(()->{
            System.out.println("查询酒店");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("订购酒店");
            return "酒店信息";
        });

        //任务3: 任务1与任务2都完成才能去订购租车服务
        CompletableFuture<String> hireCar=orderHotel.thenCombine(orderAirplane,(airplane,hotel)->{
            System.out.println("根据航班加酒店订购租车服务");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "租车信息";
        });
        //等待任务3 执行结果
        System.out.println(hireCar.join());
    }

    public static void create(){
        ExecutorService threadPool=new ThreadPoolExecutor(4,
                40,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
                );
        CompletableFuture<String> cf=CompletableFuture.supplyAsync(() ->{
            System.out.println("cf 任务执行开始");
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            System.out.println("cf 任务执行结束");
                    return "楼下小黑哥";
                },threadPool);
        try {
            threadPool.execute(() ->{
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("主动设置cf任务结束");
                //设置任务结果，由于cf任务未执行结束，结果返回true
                cf.complete("程序通事");

            });
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
        try {
            // 由于 cf 未执行结束，将会被阻塞。5 秒后，另外一个线程主动设置任务结果
            System.out.println("get:" + cf.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 等待 cf 任务执行结束
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            // 由于已经设置任务结果，cf 执行结束任务结果将会被抛弃
            System.out.println("get:" + cf.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void create2(){
        ExecutorService threadPool=new ThreadPoolExecutor(4,
                40,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        CompletableFuture<String> cf=CompletableFuture.supplyAsync(() ->{
            System.out.println("cf 任务执行开始");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf 任务执行结束");
            return "楼下小黑哥";
        },threadPool);
        try {
            threadPool.execute(() ->{
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("主动设置cf异常");
                // 设置任务结果，由于 cf 任务未执行结束，结果返回 true
                cf.completeExceptionally(new RuntimeException("啊，挂了"));
            });
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
        // 由于 cf 未执行结束，前 5 秒将会被阻塞。后续程序抛出异常，结束
        try {
            System.out.println("get:" + cf.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
