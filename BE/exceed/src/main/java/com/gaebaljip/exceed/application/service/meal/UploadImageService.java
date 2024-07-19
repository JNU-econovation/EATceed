package com.gaebaljip.exceed.application.service.meal;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.application.port.in.meal.UploadImageCommand;
import com.gaebaljip.exceed.application.port.in.meal.UploadImageUsecase;
import com.gaebaljip.exceed.application.port.out.meal.PresignedUrlPort;
import com.gaebaljip.exceed.common.exception.meal.ExtentionNotAllowedException;

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
