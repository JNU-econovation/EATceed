package com.gaebaljip.exceed.nutritionist.application.port.out;

import java.time.LocalDateTime;

import com.gaebaljip.exceed.member.domain.Member;

public interface MonthlyTargetPort {
    Member query(Long memberId, LocalDateTime date);
}
