package com.meetravel.global.cloud.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class S3Config {
    private final S3Properties properties;

    @Bean
    public AwsCredentialsProvider s3CredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(
                properties.getCredentials().getAccessKey(),
                properties.getCredentials().getSecretKey()
        ));
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(s3CredentialsProvider())
                .region(properties.getRegion())
                .build();
    }
}
