package com.resttcar.dh.entity;

public class GoodSpec{

    @Override
    public String toString() {
        return "GoodSpec{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    /**
     * name : 甜度
     * value : 三分甜,七分甜
     */

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}