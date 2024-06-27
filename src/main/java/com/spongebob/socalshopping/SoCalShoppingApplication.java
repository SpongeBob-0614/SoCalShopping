package com.spongebob.socalshopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.spongebob.socalshopping.db.mappers")
@ComponentScan(basePackages = "com.spongebob")
public class SoCalShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoCalShoppingApplication.class, args);
    }

}
