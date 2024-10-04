package com.gaebaljip.exceed.application.service.nutritionist;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.nutritionist.MealFoodsAnalyzer;
import com.gaebaljip.exceed.application.domain.nutritionist.VisitChecker;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetDaysAnalysisCommand;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetDaysAnalysisUsecase;
import com.gaebaljip.exceed.application.port.out.meal.DailyMealPort;
import com.gaebaljip.exceed.application.port.out.member.HistoryPort;
import com.gaebaljip.exceed.common.dto.DaysMealRecordDTO;
import com.gaebaljip.exceed.common.dto.GetDaysAnalysisDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetDaysAnalysisService implements GetDaysAnalysisUsecase {

    private final DailyMealPort dailyMealPort;
    private final HistoryPort historyPort;

    @Override
    public GetDaysAnalysisDTO execute(GetDaysAnalysisCommand command) {
        DaysMealRecordDTO daysMealRecordDTO =
                dailyMealPort.queryMealFoodsForDays(
                        new com.gaebaljip.exceed.common.dto.DaysMealDTO(
                                command.memberId(),
                                command.startDateTime(),
                                command.endDateTime()));
        Map<LocalDate, Member> members =
                historyPort.findMembersByDays(
                        command.memberId(),
                        command.startDateTime().toLocalDate(),
                        command.endDateTime().toLocalDate());
        MealFoodsAnalyzer analyzer =
                new MealFoodsAnalyzer(daysMealRecordDTO.mealFoodsByDate(), members);
        Map<LocalDate, Boolean> visitByDate =
                new VisitChecker(daysMealRecordDTO.mealFoodsByDate()).check();
        return GetDaysAnalysisDTO.of(analyzer.isCalorieAchievementByDate(), visitByDate);
    }
}
