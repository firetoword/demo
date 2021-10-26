package com.example.cong.bean;

import java.util.List;

public class CongVO {
    private String name;
    private  String id;
    private  String age;
    private String parentId;
    private List<CongVO> childs;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<CongVO> getChilds() {
        return childs;
    }

    public void setChilds(List<CongVO> childs) {
        this.childs = childs;
    }

    public CongVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


}
