package com.apiyoo.anthorization.swy.util;

public class StudyThread1 {
    /**
     * 将这些Runnable对象作为一个里类的内部类，共享数据做为外部类的成员变量
     * 连个多个线程共享一份数据，可将共享数据放入一个类中，d操作共享是的数据也交给该类的方法处理
     * 便于线程间的通信与互斥
     *
     * @param args
     */
    private static int count = 0;

    public static void main(String[] args) {
         MythreadData mythreadData = new MythreadData();

        StudyThread1 studyThread = new StudyThread1();
        Increment increment = studyThread.new Increment(mythreadData);
        Decrement decrement = studyThread.new Decrement(mythreadData);

        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(increment);
            t.start();

            Thread t1 = new Thread(decrement);
            t1.start();

        }
    }

    /**
     * 增加线程
     */
     class Increment implements Runnable {
        private MythreadData data1;

        public Increment(MythreadData mythreadData) {
            this.data1 = mythreadData;
        }


        @Override
        public void run() {
            data1.inc();
        }
    }

    /**
     * 减少线程
     */
     class Decrement implements Runnable {
        private MythreadData data1;

        public Decrement(MythreadData mythreadData) {
            this.data1 = mythreadData;
        }

        @Override
        public void run() {
            data1.dec();
        }

    }

     static class MythreadData {
        private int j = 0;

        /**
         * 增加方法
         */
        public synchronized void inc() {
            for (int i = 0; i < 10; i++) {
                count++;
                System.out.println("线程" + Thread.currentThread().getName() + "正在执行增加操作count=" + count);
            }
        }


        /**
         * 减少方法
         */
        public synchronized void dec() {
            for (int i = 0; i < 10; i++) {
                count--;
                System.out.println("线程" + Thread.currentThread().getName() + "正在执行减少操作count=" + count);
            }
        }

    }
}
