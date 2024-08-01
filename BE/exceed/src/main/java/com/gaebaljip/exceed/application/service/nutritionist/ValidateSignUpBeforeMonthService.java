package com.gaebaljip.exceed.application.service.nutritionist;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.out.jpa.member.MemberPersistenceAdapter;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.nutritionist.ValidateSignUpBeforeMonthUsecase;
import com.gaebaljip.exceed.common.exception.meal.InValidDateFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateSignUpBeforeMonthService implements ValidateSignUpBeforeMonthUsecase {

    private final MemberPersistenceAdapter memberPersistenceAdapter;

    @Override
    @Transactional(readOnly = true)
    public void execute(Long memberId, LocalDate targetDate) {
        MemberEntity memberEntity = memberPersistenceAdapter.query(memberId);
        if (memberEntity.checkIfBeforeSignUpMonth(targetDate, memberEntity.getCreatedDate())) {
            throw InValidDateFoundException.EXECPTION;
        }
    }
}
