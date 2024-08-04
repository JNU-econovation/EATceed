package com.gaebaljip.exceed.application.service.nutritionist;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetCalorieAnalysisRequest;
import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetCalorieAnalysisResponse;
import com.gaebaljip.exceed.adapter.out.jpa.nutritionist.MonthlyMealPort;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.nutritionist.MonthlyAnalyzer;
import com.gaebaljip.exceed.application.domain.nutritionist.MonthlyMeal;
import com.gaebaljip.exceed.application.domain.nutritionist.VisitChecker;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetCalorieAnalysisUsecase;
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
public class GetCalorieAnalysisService implements GetCalorieAnalysisUsecase {

    private final MonthlyMealPort monthlyMealPort;
    private final HistoryPort historyPort;

    /**
     * CalorieAnalyzer 도메인이 특정 날짜에 목표 칼로리를 달성했는 지를 판단하여 달성했을 경우 true를 반환
     *
     * @param request : 회원 PK와 날짜
     * @return GetAnalysisResponse : 날짜별 칼로리 달성 여부
     */
    @Override
    @Timer
    @Transactional(readOnly = true)
    public GetCalorieAnalysisResponse execute(GetCalorieAnalysisRequest request) {
        MonthlyMeal monthlyMeal =
                monthlyMealPort.query(new MonthlyMealDTO(request.memberId(), request.date()));
        Map<LocalDate, Member> members =
                historyPort.findMembersByMonth(request.memberId(), request.date());
        Map<LocalDate, Boolean> calorieAchievementByDate =
                new MonthlyAnalyzer(monthlyMeal, members).isCalorieAchievementByDate();
        Map<LocalDate, Boolean> visitByDate = new VisitChecker(monthlyMeal).check();
        return GetCalorieAnalysisResponse.of(calorieAchievementByDate, visitByDate);
    }
}
