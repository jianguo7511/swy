package com.apiyoo.anthorization.swy.util;

public class Thread4Join {

    //t1
    final Thread t1 = new Thread(new Runnable(){


        @Override
        public void run() {
            System.out.println("t1");
        }
    });
    //t2
    final Thread t2 = new Thread(new Runnable(){

        @Override
        public void run() {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2");

        }
    });
    //t3
    final Thread t3 = new Thread(new Runnable(){


        @Override
        public void run() {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t3");

        }
    });


    public static void main(String[] args) {

        Thread4Join thread4Join =new Thread4Join();

        thread4Join.t1.start();
        thread4Join.t2.start();
        thread4Join.t3.start();
    }
}
