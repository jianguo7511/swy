package com.apiyoo.anthorization.swy.controller;

public class ThreadTest2 {

    /**
     * 多个线程访问共享对象和变量有哪些方式。每个线程代码相同可使用同一个Runnable对象
     * 执行代码不同，则将Runnable对象作为同一个对象的内部类，共享数据作为另一个对象的成员变量，
     * 并将操作数据的方法也给到这个对象，形成线程的互斥与通信
     */

    ShareData shareData = new ShareData();

    class Inc implements Runnable {

        @Override
        public void run() {
            //j++;
            shareData.inc();
        }
    }

    class Dec implements Runnable {

        @Override
        public void run() {
            //j--;
            shareData.dec();
        }
    }

    class ShareData {
        int j = 100;

        //增加方法
        public synchronized void inc() {
            j++;
            System.out.println("增加线程" + Thread.currentThread().getName() + "增加,j=" + j);
        }

        //减少方法
        public synchronized void dec() {
            j--;
            System.out.println("减少线程" + Thread.currentThread().getName() + "减少,j=" + j);
        }

    }

    public static void main(String[] args) {

        ThreadTest2 thredTest2 = new ThreadTest2();
        ThreadTest2.Inc inc = thredTest2.new Inc();
        ThreadTest2.Dec dec = thredTest2.new Dec();

        for (int i = 0; i <=1 ; i++) {
            new Thread(inc).start();
            new Thread(dec).start();
        }

    }

}
