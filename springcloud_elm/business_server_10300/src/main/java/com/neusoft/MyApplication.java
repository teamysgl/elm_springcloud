package com.neusoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients  //开启Feign的支持
public class MyApplication {
    public static void main(String[] args){
        SpringApplication.run(MyApplication.class,args);
    }
}
