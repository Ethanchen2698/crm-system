package com.ethan.crmsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CrmSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmSystemApplication.class, args);
    }

}
