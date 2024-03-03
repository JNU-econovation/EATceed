package com.gaebaljip.exceed.food.adapter.in;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
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

    /**
     * 음식 정보 무한 스크롤 조회
     */
    @GetMapping("/foods")
    public ApiResponse<ApiResponse.CustomBody<Slice<GetPageableFood>>> getFoods(
            @RequestParam(value = "lastFoodName", required = false) String lastFoodName,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "10") int size
    ) {
        Slice<GetPageableFood> getPageableFoods = getFoodQuery.execute(lastFoodName, keyword ,size);
        return ApiResponseGenerator.success(getPageableFoods, HttpStatus.OK);
    }

}
