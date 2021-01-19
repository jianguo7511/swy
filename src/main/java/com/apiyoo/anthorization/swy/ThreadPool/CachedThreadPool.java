package com.apiyoo.anthorization.swy.ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CachedThreadPool {

    public static  class TestCachedThreadPool extends Thread{
        @Override
        public void run() {
            System.out.println("线程:"+Thread.currentThread().getName()+"开始执行时间："+System.currentTimeMillis());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程:"+Thread.currentThread().getName()+"结束执行时间："+System.currentTimeMillis());
        }

        public static void main(String[] args) {

            TestCachedThreadPool tp = new TestCachedThreadPool();
            ExecutorService  es = Executors.newCachedThreadPool();

            //ExecutorService  es = Executors.newFixedThreadPool(5);
            //ExecutorService  es = Executors.newSingleThreadExecutor();
//
            for (int i = 0; i < 50; i++) {
                Future f = es.submit(tp);
                System.out.println(f.isDone());
            }
            es.shutdown();
        }
    }
}
