package com.apiyoo.anthorization.swy.util;

/**
 * 锁 要用同一对象来锁，而不能用不同对象，锁不住，没有意义
 * 可以锁方法可以锁代码块，要想互斥，必须保证锁的方法和
 * 可以面向对象的方式设计代码，比如子线程循环10次,主线程100次，然后
 * 再子线程10次，主线程100次，这样循环50次，设计代码
 * 首先，循环十次 ，循环100次可当做两个对象的方法用对象进行调用
 * 然后用该对象的锁进行同步，然后根据实际执行情况
 * 进行设计
 */
public class Traditional {

    public static void main(String[] args) {

        final  Buiness buiness = new Buiness();

        //子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=50 ; i++) {
                    buiness.childSub(i);
                }
            }
        }
        ).start();


        //主线程
        for (int i = 1; i <=50 ; i++) {
            buiness.mainSub(i);
        }
    }

}
class Buiness {

    boolean mainShould = true;
    public synchronized  void mainSub(int num ){
        //没有轮到主线程执行
        if(!mainShould){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 1; i <=100 ; i++) {
            System.out.println("main线程第"+num+"次循环，打印-"+i);
        }
        mainShould = false;
        this.notify();
    }

    public synchronized  void childSub(int num){
        //
        if(mainShould){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 1; i <=10 ; i++) {
            System.out.println("child线程第"+num+"次循环，打印-"+i);
        }
        mainShould = true;
        this.notify();
    }

}
