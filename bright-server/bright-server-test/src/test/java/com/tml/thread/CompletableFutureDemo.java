package com.tml.thread;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author JacksonTu
 * @version 1.0
 * @description com.tml.thread
 * @since 2020/9/3 9:51
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        MyTask task1=new MyTask("task1",3,true);
        MyTask task2=new MyTask("task2",4,true);
        MyTask task3=new MyTask("task3",1,false);

        CompletableFuture f1=CompletableFuture.supplyAsync(()->task1.call())
                .thenAccept(result -> callback(result));
        CompletableFuture f2=CompletableFuture.supplyAsync(()->task2.call())
                .thenAccept(result -> callback(result));
        CompletableFuture f3=CompletableFuture.supplyAsync(()->task3.call())
                .thenAccept(result -> callback(result));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void callback(Boolean result){
        if(false==result){
            //处理结束流程
            //通知其他线程结束（回滚）
            //超时处理
            System.exit(0);
        }
    }



    private static class  MyTask{
        private String name;
        private int timeInSeconds;
        private boolean ret;

        public MyTask(String name, int timeInSeconds, boolean ret) {
            this.name = name;
            this.timeInSeconds = timeInSeconds;
            this.ret = ret;
        }

        public Boolean call(){
            try {
                TimeUnit.SECONDS.sleep(timeInSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name+" task callback");
            return ret;
        }
    }
}
