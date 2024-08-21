package com.gaebaljip.exceed.common.dto;

import java.util.List;

import com.gaebaljip.exceed.application.domain.food.FoodEntity;

public record PageableFoodDTO(List<FoodEntity> foodEntities, Boolean hasNext, int size) {}
