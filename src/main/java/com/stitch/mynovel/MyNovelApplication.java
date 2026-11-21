package com.stitch.mynovel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.stitch.mynovel.mapper")
public class MyNovelApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyNovelApplication.class, args);
    }
}
