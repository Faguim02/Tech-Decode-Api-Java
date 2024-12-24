package com.techdecode.blog.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class S3Service {
    @Autowired
    private AmazonS3 amazonS3;

    public void uploadFile(String bucketName, String key, MultipartFile file) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        amazonS3.putObject(bucketName, key, file.getInputStream(), objectMetadata);
    }

    public String getUrl(String bucketName, String key) {
        return amazonS3.getUrl(bucketName, key).toString();
    }
}
