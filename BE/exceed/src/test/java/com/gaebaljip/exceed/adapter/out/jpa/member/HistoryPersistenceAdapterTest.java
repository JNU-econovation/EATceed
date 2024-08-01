package com.gaebaljip.exceed.adapter.out.jpa.member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.common.DatabaseTest;

class HistoryPersistenceAdapterTest extends DatabaseTest {

    @Autowired private HistoryPersistenceAdapter historyPersistenceAdapter;

    @Test
    void findMembersByMonth() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 4, 10, 2);
        Map<LocalDate, Member> membersByMonth =
                historyPersistenceAdapter.findMembersByMonth(1L, dateTime);
        System.out.println(membersByMonth);
    }
}
