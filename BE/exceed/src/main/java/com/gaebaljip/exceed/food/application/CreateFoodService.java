package com.gaebaljip.exceed.food.application;

import com.gaebaljip.exceed.dto.request.CreateFoodRequest;
import com.gaebaljip.exceed.food.adapter.out.FoodEntity;
import com.gaebaljip.exceed.food.application.port.in.CreateFoodUseCase;
import com.gaebaljip.exceed.food.application.port.out.FoodPort;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFoodService implements CreateFoodUseCase {
    private final FoodPort foodPort;
    private final MemberPort memberPort;

    @Override
    public void execute(CreateFoodRequest request, Long memberId) {
        MemberEntity memberEntity = memberPort.query(memberId);
        FoodEntity foodEntity = FoodEntity.builder()
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
