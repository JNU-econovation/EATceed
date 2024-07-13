package com.gaebaljip.exceed.application.service.nutritionist;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.out.jpa.nutritionist.MonthlyMealPort;
import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.nutritionist.CalorieAnalyzer;
import com.gaebaljip.exceed.application.domain.nutritionist.CalorieAnalyzerFactory;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetCalorieAnalysisUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.dto.CalorieAnalysisDTO;
import com.gaebaljip.exceed.dto.MonthlyMealDTO;
import com.gaebaljip.exceed.dto.request.GetCalorieAnalysisRequest;
import com.gaebaljip.exceed.dto.response.GetCalorieAnalysisResponse;

import lombok.RequiredArgsConstructor;

/**
 * 한달치 칼로리 달성도를 날짜별로 측정하여 반환한다.
 *
 * @author hwangdaesun
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class GetCalorieCalorieAnalysisService implements GetCalorieAnalysisUsecase {

    public static final int FIRST_DAY = 1;
    private final MonthlyMealPort monthlyMealPort;
    private final MemberPort memberPort;

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
        List<Meal> meals =
                monthlyMealPort.query(new MonthlyMealDTO(request.memberId(), request.date()));
        Map<LocalDate, DailyMeal> dailyMealMap = groupByDate(meals);
        Member member = memberPort.query(request.memberId(), request.date());
        List<CalorieAnalysisDTO> analyses =
                getStartDate(request)
                        .datesUntil(getLastDate(request).plusDays(1))
                        .map(date -> createAnalysisForDay(date, dailyMealMap, member))
                        .toList();
        return new GetCalorieAnalysisResponse(analyses);
    }

    @Timer
    private Map<LocalDate, DailyMeal> groupByDate(List<Meal> meals) {
        return meals.stream()
                .collect(
                        Collectors.groupingBy(
                                meal -> meal.getMealDateTime().toLocalDate(),
                                Collectors.collectingAndThen(Collectors.toList(), DailyMeal::new)));
    }

    private LocalDate getStartDate(GetCalorieAnalysisRequest request) {
        return LocalDate.from(request.date().withDayOfMonth(FIRST_DAY));
    }

    private LocalDate getLastDate(GetCalorieAnalysisRequest request) {
        LocalDate date = request.date().toLocalDate(); // LocalDateTime을 LocalDate로 변환
        return date.withDayOfMonth(date.lengthOfMonth()); // 그 달의 마지막 날짜를 설정
    }

    @Timer
    private CalorieAnalysisDTO createAnalysisForDay(
            LocalDate day, Map<LocalDate, DailyMeal> dailyMealMap, Member member) {
        return Optional.ofNullable(dailyMealMap.get(day))
                .map(
                        dailyMeal -> {
                            CalorieAnalyzer calorieAnalyzer =
                                    CalorieAnalyzerFactory.getInstance()
                                            .createAnalyzer(dailyMeal, member);
                            return CalorieAnalysisDTO.builder()
                                    .date(day)
                                    .isVisited(true)
                                    .isCalorieAchieved(calorieAnalyzer.analyze())
                                    .build();
                        })
                .orElseGet(
                        () ->
                                CalorieAnalysisDTO.builder()
                                        .date(day)
                                        .isVisited(false)
                                        .isCalorieAchieved(false)
                                        .build());
    }
}
