package com.gaebaljip.exceed.meal.application.port.in;

import com.gaebaljip.exceed.dto.response.UploadImage;
import org.springframework.stereotype.Component;

@Component
public interface UploadImageUsecase {

    String execute(UploadImage uploadImage);
}
