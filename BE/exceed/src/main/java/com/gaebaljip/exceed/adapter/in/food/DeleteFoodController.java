package com.gaebaljip.exceed.adapter.in.food;

import com.gaebaljip.exceed.application.port.in.food.DeleteFoodUseCase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.common.docs.food.DeleteFoodExceptionDocs;
import com.gaebaljip.exceed.common.swagger.ApiErrorExceptionsExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
@Tag(name = "[음식 삭제]")
public class DeleteFoodController {

    private final DeleteFoodUseCase deleteFoodUseCase;

    @Operation(description = "내가 등록한 음식 삭제하기")
    @DeleteMapping("/food/{foodId}")
    @ApiErrorExceptionsExample(DeleteFoodExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<Void>> deleteFood(@PathVariable Long foodId,
                                                                @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        deleteFoodUseCase.deleteFood(foodId, memberId);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
