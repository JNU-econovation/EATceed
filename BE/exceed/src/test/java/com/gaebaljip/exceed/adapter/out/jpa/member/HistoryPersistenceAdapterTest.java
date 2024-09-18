package com.gaebaljip.exceed.adapter.out.jpa.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.common.DatabaseTest;

class HistoryPersistenceAdapterTest extends DatabaseTest {

    @Autowired private HistoryPersistenceAdapter historyPersistenceAdapter;

    @Test
    @DisplayName(
            "2023년 12월 4일로 조회시 2023년 12월 3일, 2023년 12월 5일, 2023년 12월 7일 기록과"
                    + "현재 회원의 기록이 반환되어야한다.."
                    + "최신 기록의 LocalDate 값은 12월 31일(조회한 월의 마지막 날)이다.")
    void when_findMembersByMonth_return_size_4() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 4, 10, 2);
        LocalDate endDate =
                dateTime.withDayOfMonth(dateTime.toLocalDate().lengthOfMonth()).toLocalDate();
        Map<LocalDate, Member> membersByMonth =
                historyPersistenceAdapter.findMembersByMonth(1L, dateTime.toLocalDate());
        Assertions.assertAll(
                () -> assertEquals(4, membersByMonth.size()),
                () -> assertEquals(membersByMonth.get(endDate).getWeight(), 74.0));
    }

    @Test
    @DisplayName(
            "2023년 11월 4일로 조회시 2023년 11월 13일, 2023년 12월 3일 기록이 반환되어야한다."
                    + "이때 2023년 12월 3일 기록의 LocalDate값은 2023년 11월 30일(조회한 월의 마지막 날)이다.")
    void when_findMembersByMonth_return_size_2() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 11, 4, 10, 2);
        LocalDate endDate =
                dateTime.withDayOfMonth(dateTime.toLocalDate().lengthOfMonth()).toLocalDate();
        Map<LocalDate, Member> membersByMonth =
                historyPersistenceAdapter.findMembersByMonth(1L, dateTime.toLocalDate());
        Assertions.assertAll(
                () -> assertEquals(2, membersByMonth.size()),
                () -> assertEquals(membersByMonth.get(endDate).getWeight(), 71.0));
    }
}
