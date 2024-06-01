package com.gaebaljip.exceed.nutritionist.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.dto.request.GetAnalysisRequest;
import com.gaebaljip.exceed.dto.request.MonthlyMeal;
import com.gaebaljip.exceed.dto.response.Analysis;
import com.gaebaljip.exceed.dto.response.GetAnalysisResponse;
import com.gaebaljip.exceed.meal.domain.DailyMeal;
import com.gaebaljip.exceed.meal.domain.Meal;
import com.gaebaljip.exceed.member.domain.Member;
import com.gaebaljip.exceed.nutritionist.application.port.in.GetAnalysisUsecase;
import com.gaebaljip.exceed.nutritionist.application.port.out.MonthlyMealPort;
import com.gaebaljip.exceed.nutritionist.application.port.out.MonthlyTargetPort;
import com.gaebaljip.exceed.nutritionist.domain.Nutritionist;

import lombok.RequiredArgsConstructor;

/**
 * 한달치 달성도를 날짜별로 측정하여 반환한다.
 *
 * @author hwangdaesun
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class GetAnalysisService implements GetAnalysisUsecase {

    public static final int FIRST_DAY = 1;
    public static final int DAYS_TO_ADD = 1;
    private final MonthlyMealPort monthlyMealPort;
    private final MonthlyTargetPort monthlyTargetPort;

    /**
     * Nutritionist 도메인이 특정 날짜에 목표 칼로리,단,탄,지를 달성했는 지를 판단하여 달성했을 경우 true를 반환 칼로리,단,단,지 달성 여부를 날짜별로
     * 그룹화한다.
     *
     * @param request : 회원 PK와 날짜
     * @return GetAnalysisResponse : 칼로리,단,단,지 달성 여부들의 집합
     */
    @Override
    @Transactional(readOnly = true)
    public GetAnalysisResponse execute(GetAnalysisRequest request) {
        List<Meal> meals =
                monthlyMealPort.query(new MonthlyMeal(request.memberId(), request.date()));
        Map<LocalDate, DailyMeal> dailyMealMap = groupByDate(meals);
        Member member = monthlyTargetPort.query(request.memberId(), request.date());
        List<Analysis> analyses =
                getStartDate(request)
                        .datesUntil(getLastDate(request).plusDays(1))
                        .map(date -> createAnalysisForDay(date, dailyMealMap, member))
                        .toList();
        return new GetAnalysisResponse(analyses);
    }

    private Map<LocalDate, DailyMeal> groupByDate(List<Meal> meals) {
        return meals.stream()
                .collect(
                        Collectors.groupingBy(
                                meal -> meal.getMealDateTime().toLocalDate(),
                                Collectors.collectingAndThen(Collectors.toList(), DailyMeal::new)));
    }

    private LocalDate getStartDate(GetAnalysisRequest request) {
        return LocalDate.from(request.date().withDayOfMonth(FIRST_DAY));
    }

    private LocalDate getLastDate(GetAnalysisRequest request) {
        LocalDate date = request.date().toLocalDate(); // LocalDateTime을 LocalDate로 변환
        return date.withDayOfMonth(date.lengthOfMonth()); // 그 달의 마지막 날짜를 설정
    }

    private Analysis createAnalysisForDay(
            LocalDate day, Map<LocalDate, DailyMeal> dailyMealMap, Member member) {
        return Optional.ofNullable(dailyMealMap.get(day))
                .map(
                        dailyMeal -> {
                            Nutritionist nutritionist =
                                    Nutritionist.createNutritionist(member, dailyMeal);
                            return Analysis.builder()
                                    .date(day)
                                    .isVisited(true)
                                    .isCalorieAchieved(nutritionist.evaluateCalorieAchieve())
                                    .build();
                        })
                .orElseGet(
                        () ->
                                Analysis.builder()
                                        .date(day)
                                        .isVisited(false)
                                        .isCalorieAchieved(false)
                                        .build());
    }
}
