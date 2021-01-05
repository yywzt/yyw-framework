package com.yyw.example.log.dto;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2020/12/29 18:10
 */
public class LogDTO {

    private String name;
    private String age;
    private String info;

    public LogDTO() {
    }

    public LogDTO(String name, String age, String info) {
        this.name = name;
        this.age = age;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
