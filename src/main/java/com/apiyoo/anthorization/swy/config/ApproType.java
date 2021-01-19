package com.apiyoo.anthorization.swy.config;

public enum ApproType {
    //完成
    FINISH("finish", 0),
    //中止
    TERMINATE("terminate", 1);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private ApproType(String name, int index) {
        this.name = name;
        this.index = index;
    }


    // 普通方法
    public static String getName(int index) {
        for (ApproType type : ApproType.values()) {
            if (type.getIndex() == index) {
                return type.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "ApproType{" +
                "name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}
