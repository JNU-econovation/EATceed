package com.gaebaljip.exceed.application.domain.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gaebaljip.exceed.common.factory.MemberEntityFixtureFactory;

class MemberEntityTest {

    @Test
    @DisplayName("checkDate 2024-6-1이고, createdDateTime이 2024-7-23 T 12:33일 경우 true")
    void when_checkIfBeforeSignUpMonth_expected_return_false() {
        // given
        MemberEntity memberEntity = MemberEntityFixtureFactory.create();
        LocalDate checkDate = LocalDate.of(2024, 6, 1);
        LocalDateTime createdDateTime = LocalDateTime.of(2024, 7, 23, 12, 33);

        // when && then
        Assertions.assertTrue(memberEntity.checkIfBeforeSignUpMonth(checkDate, createdDateTime));
    }

    @Test
    @DisplayName("checkDate 2024-7-1이고, createdDateTime이 2024-7-23 T 12:33일 경우 false")
    void when_checkIfBeforeSignUpMonth_expected_return_true() {
        // given
        MemberEntity memberEntity = MemberEntityFixtureFactory.create();
        LocalDate checkDate = LocalDate.of(2024, 7, 1);
        LocalDateTime createdDateTime = LocalDateTime.of(2024, 7, 23, 12, 33);

        // when && then
        Assertions.assertFalse(memberEntity.checkIfBeforeSignUpMonth(checkDate, createdDateTime));
    }
}
