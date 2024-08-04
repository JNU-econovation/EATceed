package com.gaebaljip.exceed.adapter.out.jpa.member.custom;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.DatabaseTest;

class CustomMemberRepositoryImplTest extends DatabaseTest {

    @Autowired private CustomMemberRepositoryImpl customMemberRepository;

    @Test
    @DisplayName(
            "회원 1L : 2023년 11월 13일에 회원가입"
                    + " 2024년 07월 02일에 회원 수정한 경우"
                    + " 2024년 07월 22일에 조회하면 회원 1L이 조회되어야한다. ")
    void when_findByIdAndDate_expected_memberEntity_1L() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 07, 22, 12, 00);
        Optional<MemberEntity> memberEntity =
                customMemberRepository.findMemberBeforeDate(1L, dateTime);
        assertAll(
                () -> assertEquals(76.0, memberEntity.get().getWeight()),
                () -> assertEquals(175.0, memberEntity.get().getHeight()));
    }

    @Test
    @DisplayName(
            "회원 1L : 2023년 11월 13일에 회원가입"
                    + " 22024년 07월 02일에 회원 수정한 경우"
                    + " 2024년 7월 1일 기준으로 조회하면, 회원이 조회되지 않는다. ")
    void when_findByIdAndDate_expected_memberEntity_null() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 7, 1, 12, 00);
        Optional<MemberEntity> memberEntity =
                customMemberRepository.findMemberBeforeDate(1L, dateTime);
        assertAll(() -> assertTrue(memberEntity.isEmpty()));
    }
}
