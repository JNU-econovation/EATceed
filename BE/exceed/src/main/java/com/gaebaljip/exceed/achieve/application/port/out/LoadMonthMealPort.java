package com.gaebaljip.exceed.achieve.application.port.out;

import com.gaebaljip.exceed.achieve.domain.DailyRecord;

import java.time.LocalDate;
import java.util.List;

public interface LoadMonthMealPort {
    List<DailyRecord> queryForMonthAchievements(Long memberId, LocalDate date);
}
