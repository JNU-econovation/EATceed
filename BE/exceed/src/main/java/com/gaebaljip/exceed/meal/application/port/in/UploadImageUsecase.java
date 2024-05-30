package com.gaebaljip.exceed.meal.application.port.in;

import org.springframework.stereotype.Component;

@Component
public interface UploadImageUsecase {

    String execute(UploadImageCommand uploadImageCommand);
}
