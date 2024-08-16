package com.gaebaljip.exceed.application.service.nutritionist;

import java.util.List;

import com.gaebaljip.exceed.common.annotation.Timer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetAllAnalysisRequest;
import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.nutritionist.*;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetAllAnalysisUsecase;
import com.gaebaljip.exceed.application.port.out.meal.DailyMealPort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.common.dto.DailyMealDTO;

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
    @Transactional(readOnly = true)
    @Timer
    public AllAnalysisDTO execute(GetAllAnalysisRequest request) {
        List<Meal> meals =
                dailyMealPort.query(new DailyMealDTO(request.memberId(), request.dateTime()));
        Member member = memberPort.query(request.memberId(), request.dateTime());
        DailyMeal dailyMeal = new DailyMeal(meals);
        DailyCalorieAnalyzer dailyCalorieAnalyzer = getCalorieAnalyzer(member, dailyMeal);
        DailyProteinAnalyzer dailyProteinAnalyzer = getProteinAnalyzer(member, dailyMeal);
        DailyCarbohydrateAnalyzer dailyCarbohydrateAnalyzer =
                getCarbohydrateAnalyzer(member, dailyMeal);
        DailyFatAnalyzer dailyFatAnalyzer = getFatAnalyzer(member, dailyMeal);
        return AllAnalysisDTO.of(
                meals,
                request.dateTime().toLocalDate(),
                dailyCalorieAnalyzer.analyze(),
                dailyProteinAnalyzer.analyze(),
                dailyCarbohydrateAnalyzer.analyze(),
                dailyFatAnalyzer.analyze());
    }

    private static DailyFatAnalyzer getFatAnalyzer(Member member, DailyMeal dailyMeal) {
        return DailyFatAnalyzerFactory.getInstance().createAnalyzer(dailyMeal, member);
    }

    private DailyCarbohydrateAnalyzer getCarbohydrateAnalyzer(Member member, DailyMeal dailyMeal) {
        return DailyCarbohydrateAnalyzerFactory.getInstance().createAnalyzer(dailyMeal, member);
    }

    private DailyProteinAnalyzer getProteinAnalyzer(Member member, DailyMeal dailyMeal) {
        return DailyProteinAnalyzerFactory.getInstance().createAnalyzer(dailyMeal, member);
    }

    private DailyCalorieAnalyzer getCalorieAnalyzer(Member member, DailyMeal dailyMeal) {
        return DailyCalorieAnalyzerFactory.getInstance().createAnalyzer(dailyMeal, member);
    }
}
