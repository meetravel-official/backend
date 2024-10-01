package com.meetravel.global.cloud.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.cloud.aws.profile.s3")
public class S3Properties {
    private S3CredentialProperties credentials;
    private String bucket;
    private Region region;
}
