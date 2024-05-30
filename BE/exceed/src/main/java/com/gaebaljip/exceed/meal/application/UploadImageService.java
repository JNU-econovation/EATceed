package com.gaebaljip.exceed.meal.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.meal.application.port.in.UploadImageCommand;
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
    public String execute(UploadImageCommand uploadImageCommand) {
        validateExt(uploadImageCommand.fileName());
        return presignedUrlPort.command(
                uploadImageCommand.memberId(),
                uploadImageCommand.mealId(),
                uploadImageCommand.fileName());
    }

    private void validateExt(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos + 1);
        if (!FILE_EXTS.contains(ext)) {
            throw ExtentionNotAllowedException.EXECPTION;
        }
    }
}
