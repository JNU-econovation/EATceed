package com.gaebaljip.exceed.achieve.application;

import com.gaebaljip.exceed.achieve.application.port.in.GetAchieveUsecase;
import com.gaebaljip.exceed.achieve.application.port.out.LoadMonthMealPort;
import com.gaebaljip.exceed.achieve.application.port.out.LoadMonthTargetPort;
import com.gaebaljip.exceed.achieve.domain.AchieveModel;
import com.gaebaljip.exceed.achieve.domain.DailyRecord;
import com.gaebaljip.exceed.achieve.domain.DailyTarget;
import com.gaebaljip.exceed.dto.response.GetAchieve;
import com.gaebaljip.exceed.dto.response.GetAchieveListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAchieveService implements GetAchieveUsecase {

    public static final int DAY_OF_MONTH = 1;
    public static final int DAYS_TO_ADD = 1;
    private final LoadMonthMealPort loadMonthMealPort;
    private final LoadMonthTargetPort loadMonthTargetPort;

    @Override
    @Transactional(readOnly = true)
    public GetAchieveListResponse execute(Long memberId, LocalDate date) {
        List<DailyRecord> dailyRecords = loadMonthMealPort.queryForMonthAchievements(memberId, date);
        DailyTarget dailyTarget = loadMonthTargetPort.queryForMonthTargets(memberId);
        AchieveModel achieveModel = AchieveModel.createAchieveModel(dailyRecords, dailyTarget);
        List<GetAchieve> getAchieves = makeGetAchieves(dailyRecords, achieveModel, getStart(date), getEnd(date));
        return new GetAchieveListResponse(getAchieves);
    }

    private LocalDate getEnd(LocalDate date) {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getMonth().maxLength());
    }

    private LocalDate getStart(LocalDate date) {
        return LocalDate.of(date.getYear(), date.getMonth(), DAY_OF_MONTH);
    }

    private List<GetAchieve> makeGetAchieves(List<DailyRecord> dailyRecords, AchieveModel achieveModel, LocalDate start, LocalDate end) {
        List<GetAchieve> getAchieves = new ArrayList<>();
        for (LocalDate day = start; day.isBefore(end); day = day.plusDays(DAYS_TO_ADD)) {
            LocalDate find = day;
            GetAchieve getAchieve = makeGetAchieve(dailyRecords, achieveModel, day, find);
            getAchieves.add(getAchieve);
        }
        return getAchieves;
    }

    private GetAchieve makeGetAchieve(List<DailyRecord> dailyRecords, AchieveModel achieveModel, LocalDate day, LocalDate find) {
        GetAchieve getAchieve = GetAchieve.builder()
                .date(day)
                .isVisited(dailyRecords.stream()
                        .anyMatch(record -> record.getDate().equals(find)))
                .proteinAchieve(achieveModel.evaluateProteinAchieve(day))
                .fatAchieve(achieveModel.evaluateFatAchieve(day))
                .carbohydrateAchieve(achieveModel.evaluateCarbohydrateAchieve(day))
                .calorieRate(achieveModel.calculateCalorieAchieveRate(day))
                .build();
        return getAchieve;
    }
}
