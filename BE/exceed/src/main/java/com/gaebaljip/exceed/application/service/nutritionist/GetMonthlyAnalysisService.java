package com.gaebaljip.exceed.application.service.nutritionist;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetMonthlyAnalysisCommand;
import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetMonthlyAnalysisResponse;
import com.gaebaljip.exceed.adapter.out.jpa.nutritionist.MonthlyMealPort;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.nutritionist.MonthlyAnalyzer;
import com.gaebaljip.exceed.application.domain.nutritionist.MonthlyMeal;
import com.gaebaljip.exceed.application.domain.nutritionist.VisitChecker;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetMonthlyAnalysisUsecase;
import com.gaebaljip.exceed.application.port.out.member.HistoryPort;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.dto.MonthlyMealDTO;

import lombok.RequiredArgsConstructor;

/**
 * 한달치 칼로리 달성도를 날짜별로 측정하여 반환한다.
 *
 * @author hwangdaesun
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class GetMonthlyAnalysisService implements GetMonthlyAnalysisUsecase {

    private final MonthlyMealPort monthlyMealPort;
    private final HistoryPort historyPort;

    /**
     * CalorieAnalyzer 도메인이 특정 날짜에 목표 칼로리를 달성했는 지를 판단하여 달성했을 경우 true를 반환
     *
     * @param command : 회원 PK와 날짜(특정 월의 첫째날)
     * @return GetAnalysisResponse : 날짜별 칼로리 달성 여부
     */
    @Override
    @Timer
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "analysis", key = "#command.memberId + '_' + #command.date")
    public String execute(GetMonthlyAnalysisCommand command) {
        MonthlyMeal monthlyMeal =
                monthlyMealPort.query(new MonthlyMealDTO(command.memberId(), command.date()));
        Map<LocalDate, Member> members =
                historyPort.findMembersByMonth(command.memberId(), command.date());
        Map<LocalDate, Boolean> calorieAchievementByDate =
                new MealFoodsAnalyzer(monthlyMeal.getMonthlyMeal(), members).isCalorieAchievementByDate();
        Map<LocalDate, Boolean> visitByDate = new VisitChecker(monthlyMeal.getMonthlyMeal()).check();
        return GetMonthlyAnalysisResponse.write(GetMonthlyAnalysisResponse.of(calorieAchievementByDate, visitByDate));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = RedisKeys.NOW_ANALYSIS_CACHE_NAME, key = "#command.memberId + '_' + #command.date")
    public String executeToNow(GetMonthlyAnalysisCommand command) {
        MonthlyMeal monthlyMeal =
                monthlyMealPort.query(new MonthlyMealDTO(command.memberId(), command.date()));
        Map<LocalDate, Member> members =
                historyPort.findMembersByMonth(command.memberId(), command.date());
        Map<LocalDate, Boolean> calorieAchievementByDate =
                new MealFoodsAnalyzer(monthlyMeal.getMonthlyMeal(), members).isCalorieAchievementByDate();
        Map<LocalDate, Boolean> visitByDate = new VisitChecker(monthlyMeal.getMonthlyMeal()).check();
        return NowMonthAnalysisCache.write(NowMonthAnalysisCache.of(LocalDate.now(), calorieAchievementByDate, visitByDate));
    }
}
