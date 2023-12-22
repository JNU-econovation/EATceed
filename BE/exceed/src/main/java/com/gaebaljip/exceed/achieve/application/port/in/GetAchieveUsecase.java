package com.gaebaljip.exceed.achieve.application.port.in;

import com.gaebaljip.exceed.dto.response.GetAchieveListResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface GetAchieveUsecase {
    GetAchieveListResponse execute(Long memberId, LocalDate date);
}
