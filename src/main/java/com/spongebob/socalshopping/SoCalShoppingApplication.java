package com.spongebob.socalshopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.spongebob.socalshopping.db.mappers")
public class SoCalShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoCalShoppingApplication.class, args);
    }

}
