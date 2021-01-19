package com.apiyoo.anthorization.swy.entity;

import org.apache.coyote.OutputBuffer;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Person implements Serializable {
    private  String name;
    private  String sex;
    private  String certid;
    private  String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCertid() {
        return certid;
    }

    public void setCertid(String certid) {
        this.certid = certid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void attackDog(){
        System.out.println("人打狗！");

    }
    public void playBasketball(){
        System.out.println("人打篮球！");

    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", certid='" + certid + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IOException {
        Person person = new Person();
        Person person2 = Person.class.newInstance();
        person.getClass().getName();

        File file = new File("E:\\1.txt");
        FileInputStream in = new FileInputStream(file);
       // BufferedOutputStream o = new BufferedOutputStream();
        InputStreamReader read = new InputStreamReader(in,"utf-8");
        BufferedReader bufferedReader = new BufferedReader(read);
        String line ="";

        StringBuffer sb = new StringBuffer();
        while (bufferedReader.ready()){
            sb.append(bufferedReader.readLine());
        }
        System.out.println(sb);
//
//        Person o = (Person) Class.forName("com.apiyoo.anthorization.swy.entity.Person").newInstance();
//        Person person1 = person.getClass().newInstance();
//
//
//        Field name = person.getClass().getDeclaredField("name");
//        Field age = person.getClass().getDeclaredField("age");
//
//        person.setAge("18");
//        name.isAccessible();
//        name.setAccessible(true);
//        name.set(person,age.get(person));
//
////         new PropertyDescriptor("name", person.getClass()).getWriteMethod().invoke(person, "宋伟亚");
////        Object invoke  = new PropertyDescriptor("name", person.getClass()).getReadMethod().invoke(person);
//        System.out.println(person);
//       // System.out.println(invoke);
    }
}
