package com.gaebaljip.exceed.application.service.nutritionist;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.nutritionist.*;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetAllAnalysisUsecase;
import com.gaebaljip.exceed.application.port.out.meal.DailyMealPort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.common.dto.TodayMealDTO;
import com.gaebaljip.exceed.common.dto.request.GetAllAnalysisRequest;

import lombok.RequiredArgsConstructor;

/**
 * 한달치 모든 영양소 달성도를 날짜별로 측정하여 반환한다.
 *
 * @author hwangdaesun
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class GetAllCalorieAnalysisService implements GetAllAnalysisUsecase {

    private final DailyMealPort dailyMealPort;
    private final MemberPort memberPort;

    /**
     * CalorieAnalyzer 도메인이 특정 날짜에 목표 칼로리를 달성했는 지를 판단하여 달성했을 경우 true를 반환
     *
     * @param request : 회원 PK와 날짜
     * @return GetAnalysisResponse : 날짜별 칼로리 달성 여부
     */
    @Override
    public AllAnalysisDTO execute(GetAllAnalysisRequest request) {
        List<Meal> meals =
                dailyMealPort.query(new TodayMealDTO(request.memberId(), request.dateTime()));
        Member member = memberPort.query(request.memberId(), request.dateTime());
        if (meals.isEmpty()) {
            return createEmptyMealAnalysis(request);
        }
        DailyMeal dailyMeal = new DailyMeal(meals);
        CalorieAnalyzer calorieAnalyzer =
                CalorieAnalyzerFactory.getInstance().createAnalyzer(dailyMeal, member);
        ProteinAnalyzer proteinAnalyzer =
                ProteinAnalyzerFactory.getInstance().createAnalyzer(dailyMeal, member);
        CarbohydrateAnalyzer carbohydrateAnalyzer =
                CarbohydrateAnalyzerFactory.getInstance().createAnalyzer(dailyMeal, member);
        FatAnalyzer fatAnalyzer =
                FatAnalyzerFactory.getInstance().createAnalyzer(dailyMeal, member);
        return AllAnalysisDTO.of(
                true,
                request.dateTime().toLocalDate(),
                calorieAnalyzer.analyze(),
                proteinAnalyzer.analyze(),
                carbohydrateAnalyzer.analyze(),
                fatAnalyzer.analyze());
    }

    public AllAnalysisDTO createEmptyMealAnalysis(GetAllAnalysisRequest request) {
        return AllAnalysisDTO.of(
                false, request.dateTime().toLocalDate(), false, false, false, false);
    }
}
