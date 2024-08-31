package com.meetravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MeetravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetravelApplication.class, args);
    }

}
