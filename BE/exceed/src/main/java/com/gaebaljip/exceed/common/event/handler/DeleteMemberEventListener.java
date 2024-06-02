package com.gaebaljip.exceed.common.event.handler;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.common.event.DeleteMemberEvent;
import com.gaebaljip.exceed.food.adapter.out.FoodEntity;
import com.gaebaljip.exceed.food.application.port.out.FoodPort;
import com.gaebaljip.exceed.meal.adapter.out.MealEntity;
import com.gaebaljip.exceed.meal.adapter.out.MealFoodEntity;
import com.gaebaljip.exceed.meal.application.port.out.MealFoodPort;
import com.gaebaljip.exceed.meal.application.port.out.MealPort;
import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.out.HistoryPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteMemberEventListener {
    private final HistoryPort historyPort;
    private final FoodPort foodPort;
    private final MealPort mealPort;
    private final MealFoodPort mealFoodPort;

    @EventListener(classes = DeleteMemberEvent.class)
    @Transactional
    public void handle(DeleteMemberEvent event) {
        mealFoodPort.deleteByAllByIdInQuery(findMfIdsByMemberEntity(event.getMemberEntity()));
        mealPort.deleteByAllByIdInQuery(findMIdsByMemberEntity(event.getMemberEntity()));
        foodPort.deleteByAllByIdInQuery(findFIdsByMemberEntity(event.getMemberEntity()));
        historyPort.deleteByAllByIdInQuery(findHIdsByMemberEntity(event.getMemberEntity()));
    }

    private List<Long> findMfIdsByMemberEntity(MemberEntity memberEntity) {
        List<MealFoodEntity> mealFoodEntities = mealFoodPort.findByMemberEntity(memberEntity);
        return mealFoodEntities.stream().map(MealFoodEntity::getId).toList();
    }

    private List<Long> findMIdsByMemberEntity(MemberEntity memberEntity) {
        List<MealEntity> mealEntities = mealPort.findByMemberEntity(memberEntity);
        return mealEntities.stream().map(MealEntity::getId).toList();
    }

    private List<Long> findFIdsByMemberEntity(MemberEntity memberEntity) {
        List<FoodEntity> foodEntities = foodPort.findByMemberEntity(memberEntity);
        return foodEntities.stream().map(FoodEntity::getId).toList();
    }

    private List<Long> findHIdsByMemberEntity(MemberEntity memberEntity) {
        List<HistoryEntity> historyEntities = historyPort.findByMemberEntity(memberEntity);
        return historyEntities.stream().map(HistoryEntity::getId).toList();
    }
}
