package com.gaebaljip.exceed.nutritionist.application;

import com.gaebaljip.exceed.dto.request.GetAnalysisRequest;
import com.gaebaljip.exceed.dto.response.Analysis;
import com.gaebaljip.exceed.nutritionist.application.port.in.GetAnalysisUsecase;
import com.gaebaljip.exceed.nutritionist.application.port.out.MonthlyMealPort;
import com.gaebaljip.exceed.nutritionist.application.port.out.MonthlyTargetPort;
import com.gaebaljip.exceed.nutritionist.domain.Nutritionist;
import com.gaebaljip.exceed.dto.request.MonthlyMeal;
import com.gaebaljip.exceed.dto.response.GetAnalysisResponse;
import com.gaebaljip.exceed.meal.domain.DailyMeal;
import com.gaebaljip.exceed.meal.domain.Meal;
import com.gaebaljip.exceed.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GetAnalysisService implements GetAnalysisUsecase {

    public static final int FIRST_DAY = 1;
    public static final int DAYS_TO_ADD = 1;
    private final MonthlyMealPort monthlyMealPort;
    private final MonthlyTargetPort monthlyTargetPort;

    @Override
    @Transactional(readOnly = true)
    public GetAnalysisResponse execute(GetAnalysisRequest request) {
        List<Meal> meals = monthlyMealPort.query(new MonthlyMeal(request.memberId(), request.date()));
        Map<LocalDate, DailyMeal> dailyMealMap = groupByDate(meals);
        Member member = monthlyTargetPort.query(request.memberId(), request.date());
        List<Analysis> analyses = Stream.iterate(getStartDate(request), day -> day.plusDays(DAYS_TO_ADD))
                .limit(getLengthOfMonth(request))
                .map(day -> createAnalysisForDay(day, dailyMealMap, member))
                .toList();
        return new GetAnalysisResponse(analyses);
    }

    private Map<LocalDate, DailyMeal> groupByDate(List<Meal> meals) {
        return meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getMealDateTime().toLocalDate(),
                        Collectors.collectingAndThen(Collectors.toList(), DailyMeal::new)));
    }

    private LocalDate getStartDate(GetAnalysisRequest request) {
        return request.date().withDayOfMonth(FIRST_DAY);
    }

    private int getLengthOfMonth(GetAnalysisRequest request) {
        return request.date().lengthOfMonth();
    }

    private Analysis createAnalysisForDay(LocalDate day, Map<LocalDate, DailyMeal> dailyMealMap, Member member) {
        return Optional.ofNullable(dailyMealMap.get(day))
                .map(dailyMeal -> {
                    Nutritionist nutritionist = Nutritionist.createNutritionist(member, dailyMeal);
                    return Analysis.builder()
                            .date(day)
                            .isVisited(true)
                            .proteinAchieve(nutritionist.evaluateProteinAchieve())
                            .fatAchieve(nutritionist.evaluateFatAchieve())
                            .carbohydrateAchieve(nutritionist.evaluateCarbohydrateAchieve())
                            .calorieRate(nutritionist.calculateCalorieAchieveRate())
                            .build();
                })
                .orElseGet(() -> Analysis.builder()
                        .date(day)
                        .isVisited(false)
                        .proteinAchieve(false)
                        .fatAchieve(false)
                        .carbohydrateAchieve(false)
                        .calorieRate(0)
                        .build());
    }
}
