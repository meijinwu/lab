package com.mofa.labsentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LabSentinelApplication {

    public static void main(String[] args) {
        // <X> 设置系统属性 project.name，提供给 Sentinel 读取
        System.setProperty("project.name", "demo-application");
        SpringApplication.run(LabSentinelApplication.class, args);
    }

}
