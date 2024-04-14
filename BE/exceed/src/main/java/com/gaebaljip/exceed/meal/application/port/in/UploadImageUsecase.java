package com.gaebaljip.exceed.meal.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.UploadImage;

@Component
public interface UploadImageUsecase {

    String execute(UploadImage uploadImage);
}
