package com.gaebaljip.exceed.application.service.nutritionist;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetCalorieAnalysisRequest;
import com.gaebaljip.exceed.adapter.in.nutritionist.response.GetCalorieAnalysisResponse;
import com.gaebaljip.exceed.adapter.out.jpa.nutritionist.MonthlyMealPort;
import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.nutritionist.DailyCalorieAnalyzer;
import com.gaebaljip.exceed.application.domain.nutritionist.DailyCalorieAnalyzerFactory;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetCalorieAnalysisUsecase;
import com.gaebaljip.exceed.application.port.out.member.HistoryPort;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;
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

    public static final int FIRST_DAY = 1;
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
        List<Meal> meals =
                monthlyMealPort.query(new MonthlyMealDTO(request.memberId(), request.date()));
        Map<LocalDate, DailyMeal> dailyMealMap = groupByDate(meals);
        Map<LocalDate, Member> membersOfDate =
                historyPort.findMembersByMonth(request.memberId(), request.date());
        List<CalorieAnalysisDTO> analyses =
                getStartDate(request)
                        .datesUntil(getLastDate(request.date().toLocalDate()).plusDays(1))
                        .map(date -> createAnalysisForDay(date, dailyMealMap, membersOfDate))
                        .toList();
        return new GetCalorieAnalysisResponse(analyses);
    }

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

    private LocalDate getLastDate(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth()); // 그 달의 마지막 날짜를 설정
    }

    private CalorieAnalysisDTO createAnalysisForDay(
            LocalDate day,
            Map<LocalDate, DailyMeal> dailyMealMap,
            Map<LocalDate, Member> membersOfDate) {
        return Optional.ofNullable(dailyMealMap.get(day))
                .map(
                        dailyMeal -> {
                            Member memberByDate = getMemberByDate(day, membersOfDate);
                            DailyCalorieAnalyzer dailyCalorieAnalyzer =
                                    DailyCalorieAnalyzerFactory.getInstance()
                                            .createAnalyzer(dailyMeal, memberByDate);
                            return CalorieAnalysisDTO.builder()
                                    .date(day)
                                    .isVisited(true)
                                    .isCalorieAchieved(dailyCalorieAnalyzer.analyze())
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

    private Member getMemberByDate(LocalDate day, Map<LocalDate, Member> membersOfDate) {
        return membersOfDate.entrySet().stream()
                .filter(entry -> entry.getKey().isAfter(day) || entry.getKey().equals(day))
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(membersOfDate.get(getLastDate(day)));
    }
}
