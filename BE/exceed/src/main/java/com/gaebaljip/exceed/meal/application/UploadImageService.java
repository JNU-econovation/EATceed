package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.response.UploadImage;
import com.gaebaljip.exceed.meal.application.port.in.UploadImageUsecase;
import com.gaebaljip.exceed.meal.exception.ExtentionNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadImageService implements UploadImageUsecase {

    public static final String CONTENT_TYPE_PREFIX = "image/";
    public static final List<String> FILE_EXTS = List.of("jpg", "jpeg", "png");
    private final S3Presigner s3Presigner;
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public String execute(UploadImage uploadImage) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uploadImage.memberId() + "_" + uploadImage.mealId())
                // todo security 도입 후 key 암호화
                .contentType(getContentType(uploadImage.fileName()))
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
        validateExt(ext);
        return CONTENT_TYPE_PREFIX + ext;
    }

    private void validateExt(String ext) {
        if (!FILE_EXTS.contains(ext)) {
            throw new ExtentionNotAllowedException();
        }
    }
}
