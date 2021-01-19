package com.apiyoo.anthorization.swy.util;

import java.util.Random;

/**
 * 冒泡排序算法
 */
public class PopSort {

    public static void Swap( int n[],int i, int j) {
        int t = 0;
        t = n[i];
        n[i] = n[j];
        n[j] = t;
    }


    public static void main(String[] args) {
        Random r = new Random();
        int[] a = new int[10];
        for (int i = 0; i < 10; i++) {
            a[i] = r.nextInt(10);
        }

        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

        int l = a.length;
        for (int i = 0; i < l - 1; i++) {
            for (int j = 0; j < l - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                  PopSort.Swap(a, j, j + 1);
//                    int t =0;
//                    t = a[j];
//                    a[j]=a[j+1];
//                    a[j+1] =t;
                }
            }
        }

        System.out.println("=============================================");
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
