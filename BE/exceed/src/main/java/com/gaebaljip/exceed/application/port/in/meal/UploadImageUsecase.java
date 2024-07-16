package com.gaebaljip.exceed.application.port.in.meal;

import org.springframework.stereotype.Component;

@Component
public interface UploadImageUsecase {

    String execute(UploadImageCommand uploadImageCommand);
}
