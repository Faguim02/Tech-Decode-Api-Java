package com.techdecode.blog.services.aws.s3;

import com.techdecode.blog.services.aws.DepedencyFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;

public class awsS3Service {
    private final S3Client s3Client;

    public awsS3Service(S3Client s3Client) {
        this.s3Client = DepedencyFactory.s3Client();
    }

    public void listBuckets(S3Client s3Client) {
        try {

            ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
            List<Bucket> bucketList = listBucketsResponse.buckets();
            bucketList.forEach(bucket -> {
                System.out.println(bucket.name());
            });

        } catch (S3Exception error) {
            System.out.println("Erro");
        }
    }

    public void createBucket(S3Client s3Client, String bucketName) {
        try {

            Region region = Region.US_EAST_1;
            S3Client s3 = S3Client.builder()
                            .region(region)
                            .build();

            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());

            s3Client.waiter().waitUntilBucketExists(HeadBucketRequest.builder().bucket(bucketName).build());

        } catch (S3Exception erro) {

        }
    }
}