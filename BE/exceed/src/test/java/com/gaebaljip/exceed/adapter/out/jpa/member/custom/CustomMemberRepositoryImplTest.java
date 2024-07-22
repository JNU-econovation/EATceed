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
            "회원 1L : 2023년 11월 20일에 회원가입"
                    + " 2023년 12월 01일에 회원 수정하신 경우"
                    + " 2024년 07월 22일에 조회하면 2023년 12월 1일의 회원이 조회되야한다. ")
    void when_findByIdAndDate_expected_memberEntity_1L() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 07, 22, 12, 00);
        Optional<MemberEntity> memberEntity =
                customMemberRepository.findMemberBeforeDate(1L, dateTime);
        assertAll(
                () -> assertEquals(70.0, memberEntity.get().getWeight()),
                () -> assertEquals(175.0, memberEntity.get().getHeight()));
    }

    @Test
    @DisplayName(
            "회원 1L : 2023년 11월 20일에 회원가입"
                    + " 2023년 12월 01일에 회원 수정하신 경우"
                    + " 2023년 11월 24일 기준으로 조회하면, 회원이 조회되지 않는다. ")
    void when_findByIdAndDate_expected_memberEntity_null() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 11, 24, 12, 00);
        Optional<MemberEntity> memberEntity =
                customMemberRepository.findMemberBeforeDate(1L, dateTime);
        assertAll(() -> assertTrue(memberEntity.isEmpty()));
    }
}
