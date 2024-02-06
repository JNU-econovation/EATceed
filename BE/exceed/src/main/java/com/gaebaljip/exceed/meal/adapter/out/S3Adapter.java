package com.gaebaljip.exceed.meal.adapter.out;

import com.gaebaljip.exceed.meal.application.port.out.GetPresignedUrlPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class S3Adapter implements GetPresignedUrlPort {

    private final S3Presigner s3Presigner;
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;
    public static final String CONTENT_TYPE_PREFIX = "image/";

    @Override
    public String command(Long memberId, Long mealId) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(memberId + "_" + mealId)
                .build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }

    @Override
    public String command(Long memberId, Long mealId, String fileName) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(memberId + "_" + mealId)
                // todo security 도입 후 key 암호화
                .contentType(getContentType(fileName))
                .build();
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(putObjectRequest)
                .build();
        return s3Presigner.presignPutObject(presignRequest).url().toString();
    }

    private String getContentType(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos + 1);
        return CONTENT_TYPE_PREFIX + ext;
    }


}
