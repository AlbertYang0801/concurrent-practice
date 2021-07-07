package com.albert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Albert
 * @date 2020/8/14 17:50
 */
@EnableAsync
@SpringBootApplication
public class ConcurrentPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrentPracticeApplication.class, args);
    }

}
