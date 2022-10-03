package com.example.lab.feign3.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LabFeign3UserServiceApplication {

    public static void main(String[] args) {
        // 设置端口
        System.setProperty("server.port", "18080");
        SpringApplication.run(LabFeign3UserServiceApplication.class, args);
    }

}
