package com.apiyoo.anthorization.swy.util;

import java.util.Arrays;
import java.util.Random;

public class TestC {
    public static void main(String[] args) {
        int[] a = new int[20];
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            a[i] = random.nextInt(20);
            //System.out.println(random.nextInt(10));
        }

        for (int i = 0; i < 20; i++) {
            // a[i] = random.nextInt(10);
            System.out.println("打印数组=" + a[i]);
        }

        //System.out.println("打印数组="+ Arrays.asList(a));
        for (int i = 0; i < a.length-1 ; i++) {
            for (int j = 0; j < a.length -1 - i; j++) {
                if (a[j] <  a[j + 1]) {
                    int x = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = x;
                }
            }

        }

        for (int q = 0; q <a.length ; q++) {
            System.out.println(a[q]);
        }
    }

}
