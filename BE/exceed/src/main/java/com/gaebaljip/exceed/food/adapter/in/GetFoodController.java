package com.gaebaljip.exceed.food.adapter.in;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.dto.response.GetFood;
import com.gaebaljip.exceed.dto.response.GetPageableFood;
import com.gaebaljip.exceed.food.application.in.GetFoodQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class GetFoodController {

    private final GetFoodQuery getFoodQuery;

    @GetMapping("/food/{id}")
    public ApiResponse<ApiResponse.CustomBody<GetFood>> getFood(@PathVariable Long id) {
        GetFood getFood = getFoodQuery.execute(id);
        return ApiResponseGenerator.success(getFood, HttpStatus.OK);
    }

    @GetMapping("/foods")
    public ApiResponse<ApiResponse.CustomBody<Slice<GetPageableFood>>> getFoods(
            @RequestParam(value = "lastFoodName", required = false) String lastFoodName,
            @RequestParam(defaultValue = "10") int size
    ) {
        Slice<GetPageableFood> getPageableFoods = getFoodQuery.execute(lastFoodName, size);
        return ApiResponseGenerator.success(getPageableFoods, HttpStatus.OK);
    }

}
