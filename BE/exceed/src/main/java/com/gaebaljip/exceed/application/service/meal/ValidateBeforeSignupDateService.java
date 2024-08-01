package com.gaebaljip.exceed.application.service.meal;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.adapter.out.jpa.member.MemberPersistenceAdapter;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.meal.ValidateBeforeSignUpDateUsecase;
import com.gaebaljip.exceed.common.exception.meal.InValidDateFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateBeforeSignupDateService implements ValidateBeforeSignUpDateUsecase {

    private final MemberPersistenceAdapter memberPersistenceAdapter;

    @Override
    public void execute(Long memberId, LocalDateTime dateTime) {
        MemberEntity memberEntity = memberPersistenceAdapter.query(memberId);
        if (memberEntity.checkIfBeforeSignUpDate(dateTime, memberEntity.getCreatedDate())) {
            throw InValidDateFoundException.EXECPTION;
        }
    }
}
