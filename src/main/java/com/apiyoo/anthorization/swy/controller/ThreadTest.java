package com.apiyoo.anthorization.swy.controller;

public class ThreadTest {

    private int j=10;


    class Inc implements Runnable {

        @Override
        public void run() {
            //j++;
            inc();
        }
    }

    class Dec implements Runnable {

        @Override
        public void run() {
            //j--;
            dec();
        }
    }

    //增加方法
    public synchronized void inc() {
        j++;
        System.out.println("增加线程"+Thread.currentThread().getName()+"增加,j="+j);
    }

    //减少方法
    public synchronized void dec() {
        j--;
        System.out.println("减少线程"+Thread.currentThread().getName()+"减少,j="+j);
    }

    public static void main(String[] args) {



        ThreadTest thredTest = new ThreadTest();
        Inc inc = thredTest.new Inc();
        Dec dec = thredTest.new Dec();
        for (int i = 0; i <=1 ; i++) {
            new Thread(inc).start();
            new Thread(dec).start();
        }
    }

}
