package com.example.cong.bean;

public class Test {

    private String id;
    private String name;

    public Test(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "test{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
