package com.apiyoo.anthorization.swy.util;

import java.util.concurrent.atomic.AtomicBoolean;

public class A {
    private  String time;
    public A(String time){
      this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static void main(String[] args) {
        AtomicBoolean atomicBoolean =new AtomicBoolean();
        atomicBoolean.getAndSet(false);

    }
}
