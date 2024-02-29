package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.request.DailyMeal;
import com.gaebaljip.exceed.dto.response.CurrentMeal;
import com.gaebaljip.exceed.meal.domain.Meal;
import com.gaebaljip.exceed.meal.application.port.out.DailyMealPort;
import com.gaebaljip.exceed.meal.application.port.in.GetCurrentMealQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCurrentMealService implements GetCurrentMealQuery {

    public static final double ZERO = 0.0;
    private final DailyMealPort dailyMealPort;

    @Override
    @Transactional(readOnly = true)
    public CurrentMeal execute(Long memberId) {
        List<Meal> meals = dailyMealPort.query(new DailyMeal(memberId, LocalDate.now()));
        if(meals.isEmpty()){
            return CurrentMeal.builder()
                    .calorie(ZERO)
                    .carbohydrate(ZERO)
                    .fat(ZERO)
                    .protein(ZERO)
                    .build();
        }else {
            com.gaebaljip.exceed.meal.domain.DailyMeal dailyMeal = new com.gaebaljip.exceed.meal.domain.DailyMeal(meals);
            return CurrentMeal.builder()
                    .calorie(dailyMeal.calculateCurrentCalorie())
                    .carbohydrate(dailyMeal.calculateCurrentCarbohydrate())
                    .fat(dailyMeal.calculateCurrentFat())
                    .protein(dailyMeal.calculateCurrentProtein())
                    .build();
        }
    }
}
