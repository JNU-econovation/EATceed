package com.gaebaljip.exceed.health;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;

@RestController
@RequestMapping("/api")
public class HealthController {
    @GetMapping("/health")
    public ApiResponse<ApiResponse.CustomBody<Void>> health() {
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
