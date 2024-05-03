package com.gaebaljip.exceed.food.adapter.in;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.dto.response.GetFoodsResponse;
import com.gaebaljip.exceed.food.application.in.GetFoodQuery;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
@Tag(name = "[음식 조회]")
public class GetFoodController {

    private final GetFoodQuery getFoodQuery;

    /** 음식 정보 무한 스크롤 조회 */
    //    @GetMapping("/foods")
    //    @Operation(summary = "음식 정보 조회", description = "음식 정보를 무한 스크롤로 조회한다.")
    //    public ApiResponse<ApiResponse.CustomBody<Slice<GetPageableFood>>> getFoods(
    //            @RequestParam(value = "lastFoodName", required = false) String lastFoodName,
    //            @RequestParam(value = "keyword", required = false) String keyword,
    //            @RequestParam(defaultValue = "10") int size) {
    //        Slice<GetPageableFood> getPageableFoods = getFoodQuery.execute(lastFoodName, keyword,
    // size);
    //        return ApiResponseGenerator.success(getPageableFoods, HttpStatus.OK);
    //    }

    @GetMapping("/foods/auto")
    public ApiResponse<ApiResponse.CustomBody<GetFoodsResponse>> getFoods(
            @RequestParam String prefix) {
        GetFoodsResponse response = getFoodQuery.execute(prefix);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }
}
