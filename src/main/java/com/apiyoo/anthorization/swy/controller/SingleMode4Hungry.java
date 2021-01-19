package com.apiyoo.anthorization.swy.controller;

/**
 * 饿汉式 怕饿死了。直接在类加载的时候创建好
 */
public class SingleMode4Hungry {

    /**
     * 构造方法私有化，防止类外实例化
     */
    private SingleMode4Hungry() {
    }


    /**
     * 饿汉式 怕饿死了。直接在类加载的时候创建好
     */

//    private static  SingleMode4Hungry getInstance(){
//       return singleMode4Hungry;
//    }
//
//    private  static  final  SingleMode4Hungry singleMode4Hungry =new SingleMode4Hungry();


    /**
     * 饱汉式  并不急着吃，需要使用的时候再创建
     */


    private static SingleMode4Hungry singleMode4Hungry;
//
//    private static synchronized SingleMode4Hungry getInstance() {
//
//        if (singleMode4Hungry == null) {
//        singleMode4Hungry = new SingleMode4Hungry();
//    }
//        return singleMode4Hungry;
//}

    /**
     * 双重锁模式(根据饱汉模式改写)
     */
    private static SingleMode4Hungry getInstance() {

        if (singleMode4Hungry == null) {
//            Thread A,B,C,D
            synchronized (SingleMode4Hungry.class) {
                if (singleMode4Hungry == null) {
                    singleMode4Hungry = new SingleMode4Hungry();
                }
            }
        }
        return singleMode4Hungry;
    }

}
