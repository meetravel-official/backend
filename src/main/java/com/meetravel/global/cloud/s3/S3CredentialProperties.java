package com.meetravel.global.cloud.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.cloud.aws.profile.s3.credentials")
public class S3CredentialProperties {
    private String accessKey;
    private String secretKey;
}
