package com.meetravel;

import io.awspring.cloud.autoconfigure.config.secretsmanager.SecretsManagerAutoConfiguration;
import io.awspring.cloud.autoconfigure.s3.S3AutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(
        exclude = {
                S3AutoConfiguration.class,
                SecretsManagerAutoConfiguration.class
        }
)
public class MeetravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetravelApplication.class, args);
    }

}
