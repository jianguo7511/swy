package com.apiyoo.anthorization.swy.util;

import lombok.Data;

import java.util.Date;
@Data
public class Star {
    private String name;
    private Date birthday;

    public Star(){}
    public Star(String name, Date birthday) {
        super();
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public static void main(String[] args) {
        Date date=new Date(1346545646556465131L);
        //new一个明星：杨洋
//        Star  yangYang=new Star("杨洋",date);
//        System.out.println(yangYang);
//        System.out.println(yangYang.getName());
//        System.out.println(yangYang.getBirthday());
//
//        date.setTime(4685456456456465465L);
//        System.out.println(yangYang.getBirthday());
//
//        //克隆杨洋:满足你当明星的梦
//        Star me= null;
//        try {
//            me = (Star) yangYang.clone();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(me);
//        me.setName("周杰伦");
//        System.out.println(me.getName());
//        System.out.println(me.getBirthday());
        String s1= new String("hello");
        String s2 = "hello";
        String s3 = "hello";
        String s4= new String("hello");
        System.out.println(s1==s4);
        System.out.println(s1.intern());

        System.out.println("===="+Integer.MAX_VALUE);


    }
}
