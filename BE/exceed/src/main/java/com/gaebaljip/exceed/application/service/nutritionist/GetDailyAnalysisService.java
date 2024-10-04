package com.gaebaljip.exceed.application.service.nutritionist;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.nutritionist.*;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetDailyAnalysisCommand;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetDailyAnalysisUsecase;
import com.gaebaljip.exceed.application.port.out.meal.DailyMealPort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;
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
public class GetDailyAnalysisService implements GetDailyAnalysisUsecase {

    private final DailyMealPort dailyMealPort;
    private final MemberPort memberPort;

    /**
     * 특정 날짜에 각 영양소 기준치를 달성했는 지를 판단하여 달성했을 경우 true를 반환
     *
     * @param request : 회원 PK와 날짜
     * @return AllAnalysisDTO : 영양소 달성 여부 및 방문 여부
     */
    @Override
    @Transactional(readOnly = true)
    public AllAnalysisDTO executeToAllNutrition(GetDailyAnalysisCommand request) {
        DailyMealFoods dailyMealFoods =
                dailyMealPort.queryMealFoodsForDay(
                        new DailyMealDTO(request.memberId(), request.dateTime()));
        Member member = memberPort.findMemberByDate(request.memberId(), request.dateTime());
        DailyCalorieAnalyzer dailyCalorieAnalyzer = getCalorieAnalyzer(member, dailyMealFoods);
        DailyProteinAnalyzer dailyProteinAnalyzer = getProteinAnalyzer(member, dailyMealFoods);
        DailyCarbohydrateAnalyzer dailyCarbohydrateAnalyzer =
                getCarbohydrateAnalyzer(member, dailyMealFoods);
        DailyFatAnalyzer dailyFatAnalyzer = getFatAnalyzer(member, dailyMealFoods);
        return AllAnalysisDTO.of(
                dailyMealFoods,
                request.dateTime().toLocalDate(),
                dailyCalorieAnalyzer.analyze(),
                dailyProteinAnalyzer.analyze(),
                dailyCarbohydrateAnalyzer.analyze(),
                dailyFatAnalyzer.analyze());
    }

    @Override
    @Transactional(readOnly = true)
    public CalorieAnalysisDTO executeToCalorie(GetDailyAnalysisCommand request) {
        DailyMealFoods dailyMealFoods =
                dailyMealPort.queryMealFoodsForDay(
                        new DailyMealDTO(request.memberId(), request.dateTime()));
        Member member = memberPort.findMemberByDate(request.memberId(), request.dateTime());
        DailyCalorieAnalyzer dailyCalorieAnalyzer = getCalorieAnalyzer(member, dailyMealFoods);
        return CalorieAnalysisDTO.of(
                dailyMealFoods, request.dateTime().toLocalDate(), dailyCalorieAnalyzer.analyze());
    }

    private static DailyFatAnalyzer getFatAnalyzer(Member member, DailyMealFoods dailyMealFoods) {
        return DailyFatAnalyzerFactory.getInstance().createAnalyzer(dailyMealFoods, member);
    }

    private DailyCarbohydrateAnalyzer getCarbohydrateAnalyzer(
            Member member, DailyMealFoods dailyMealFoods) {
        return DailyCarbohydrateAnalyzerFactory.getInstance()
                .createAnalyzer(dailyMealFoods, member);
    }

    private DailyProteinAnalyzer getProteinAnalyzer(Member member, DailyMealFoods dailyMealFoods) {
        return DailyProteinAnalyzerFactory.getInstance().createAnalyzer(dailyMealFoods, member);
    }

    private DailyCalorieAnalyzer getCalorieAnalyzer(Member member, DailyMealFoods dailyMealFoods) {
        return DailyCalorieAnalyzerFactory.getInstance().createAnalyzer(dailyMealFoods, member);
    }
}
