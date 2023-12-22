package com.gaebaljip.exceed.meal.adapter.in;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponse.CustomBody;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.dto.EatMealRequest;
import com.gaebaljip.exceed.meal.application.port.in.EatMealCommand;
import com.gaebaljip.exceed.meal.application.port.in.EatMealUsecase;
import com.gaebaljip.exceed.meal.application.port.in.GetPreSignedUrlUsecase;
import com.gaebaljip.exceed.meal.domain.MealType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class EatMealController {

    private final EatMealUsecase eatMealUsecase;
    private final GetPreSignedUrlUsecase getPreSignedUrlUsecase;

    @PostMapping("/meal")
    public ApiResponse<CustomBody<Void>> eatMeal(@Valid @RequestBody EatMealRequest request) {
        EatMealCommand eatMealCommand = EatMealCommand.builder()
                .foodIds(request.foodIds())
                .mealType(MealType.valueOf(request.mealType()))
                .multiple(request.multiple())
                .memberId(1L)
                .build();
        Long mealId = eatMealUsecase.execute(eatMealCommand);
        getPreSignedUrlUsecase.execute(1L, mealId);
        return ApiResponseGenerator.success(HttpStatus.CREATED);
    }
}
