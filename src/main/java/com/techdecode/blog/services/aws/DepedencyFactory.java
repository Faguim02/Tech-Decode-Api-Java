package com.techdecode.blog.services.aws;

import org.apache.http.impl.client.BasicCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.s3.S3Client;

public class DepedencyFactory {
    private DepedencyFactory() {}

    public static S3Client s3Client() {
        return S3Client.builder()
                .httpClientBuilder(ApacheHttpClient.builder()).build();
    }
}
