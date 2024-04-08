package com.gaebaljip.exceed.meal.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.dto.response.UploadImage;
import com.gaebaljip.exceed.meal.application.port.in.UploadImageUsecase;
import com.gaebaljip.exceed.meal.application.port.out.PresignedUrlPort;
import com.gaebaljip.exceed.meal.exception.ExtentionNotAllowedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadImageService implements UploadImageUsecase {
    public static final List<String> FILE_EXTS = List.of("jpg", "jpeg", "png");
    public final PresignedUrlPort presignedUrlPort;

    @Override
    public String execute(UploadImage uploadImage) {
        validateExt(uploadImage.fileName());
        return presignedUrlPort.command(
                uploadImage.memberId(), uploadImage.mealId(), uploadImage.fileName());
    }

    private void validateExt(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos + 1);
        if (!FILE_EXTS.contains(ext)) {
            throw ExtentionNotAllowedException.EXECPTION;
        }
    }
}
