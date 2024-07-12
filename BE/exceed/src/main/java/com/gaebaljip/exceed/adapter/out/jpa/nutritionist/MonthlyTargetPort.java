package com.gaebaljip.exceed.adapter.out.jpa.nutritionist;

import java.time.LocalDateTime;

import com.gaebaljip.exceed.application.domain.member.Member;

public interface MonthlyTargetPort {

    Member query(Long memberId, LocalDateTime date);
}
