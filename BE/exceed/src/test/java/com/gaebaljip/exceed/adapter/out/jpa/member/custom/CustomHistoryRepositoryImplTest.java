package com.gaebaljip.exceed.adapter.out.jpa.member.custom;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.common.DatabaseTest;

class CustomHistoryRepositoryImplTest extends DatabaseTest {

    @Autowired private CustomHistoryRepositoryImpl customHistoryRepository;

    @Test
    @DisplayName(
            "회원 1L : 2023-12-03 11:00:00, 2023-12-05 09:00:00, 그리고 2023-12-07 09:00:00에 회원 수정"
                    + "2023-12-04의 회원 정보를 열람할 경우 2023-12-05 09:00:00 에 저장된 회원 정보를 조회해야한다.")
    void when_findRecentFutureMemberWeight_expected_72() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 04, 12, 00);
        HistoryEntity historyEntity =
                customHistoryRepository.findRecentFutureMember(1L, dateTime).get();
        assertEquals(historyEntity.getWeight(), 72.0);
    }
}
