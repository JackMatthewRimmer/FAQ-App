package com.faq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.faq.common")
public class API {
    public static void main(String[] args) {
        SpringApplication.run(API.class, args);
    }
}