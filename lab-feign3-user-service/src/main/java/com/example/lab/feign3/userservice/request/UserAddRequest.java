package com.example.lab.feign3.userservice.request;

public class UserAddRequest {

    private String name;
    private Integer gender;

    public String getName() {
        return name;
    }

    public UserAddRequest setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public UserAddRequest setGender(Integer gender) {
        this.gender = gender;
        return this;
    }
}
