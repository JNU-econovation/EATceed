package com.gaebaljip.exceed.application.service.food;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.adapter.in.food.request.CreateFoodRequest;
import com.gaebaljip.exceed.application.domain.food.FoodEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.food.CreateFoodUseCase;
import com.gaebaljip.exceed.application.port.out.food.FoodPort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateFoodService implements CreateFoodUseCase {
    private final FoodPort foodPort;
    private final MemberPort memberPort;

    @Override
    public void execute(CreateFoodRequest request, Long memberId) {
        MemberEntity memberEntity = memberPort.query(memberId);
        FoodEntity foodEntity =
                FoodEntity.builder()
                        .calorie(request.calorie())
                        .dietaryFiber(request.dietaryFiber())
                        .carbohydrate(request.carbohydrate())
                        .name(request.name())
                        .protein(request.protein())
                        .servingSize(request.servingSize())
                        .sodium(request.sodium())
                        .sugars(request.sugars())
                        .fat(request.fat())
                        .memberEntity(memberEntity)
                        .build();
        foodPort.command(foodEntity);
    }
}
