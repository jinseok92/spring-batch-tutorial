package com.tutorial.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing  // Spring Batch 기능 활성화
@SpringBootApplication
public class SpringBatchTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchTutorialApplication.class, args);
    }

}