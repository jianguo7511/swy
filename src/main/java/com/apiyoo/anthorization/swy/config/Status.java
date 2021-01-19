package com.apiyoo.anthorization.swy.config;

public enum Status {
    CHECKING("等待审批", 0),
    SUCCESS("授权成功", 2),
    FAIL("授权失败", 4),
    PROCESSING("审批中", 6);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private Status(String name, int index) {
        this.name = name;
        this.index = index;
    }


    // 普通方法
    public static String getName(int index) {
        for (Status status : Status.values()) {
            if (status.getIndex() == index) {
                return status.name;
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
        return "Status{" +
                "name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}
