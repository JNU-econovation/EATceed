package com.gaebaljip.exceed.nutritionist.application.port.out;

import com.gaebaljip.exceed.member.domain.Member;

import java.time.LocalDate;

public interface MonthlyTargetPort {
    Member query(Long memberId, LocalDate date);
}
