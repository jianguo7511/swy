package com.apiyoo.anthorization.swy.util;

public class StudyThread {
    static int j = 0;

    public static void main(String[] args) {

        StudyThread studyThread = new StudyThread();
        Increment increment = studyThread.new Increment();
        Decrement decrement = studyThread.new Decrement();
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
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                inc();
            }
        }
    }

    /**
     * 减少线程
     */
    class Decrement implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                dec();
            }
        }
    }

    /**
     * 增加方法
     */
    public static synchronized void inc() {
        j++;
        System.out.println("线程" + Thread.currentThread().getName() + "正在执行增加操作j=" + j);
    }

    /**
     * 减少方法
     */
    public static synchronized void dec() {
        j--;
        System.out.println("线程" + Thread.currentThread().getName() + "正在执行减少操作j=" + j);
    }
}
