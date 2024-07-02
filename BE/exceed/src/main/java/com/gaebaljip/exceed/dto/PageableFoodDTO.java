package com.gaebaljip.exceed.dto;

import java.util.List;

import com.gaebaljip.exceed.food.adapter.out.FoodEntity;

public record PageableFoodDTO(List<FoodEntity> foodEntities, Boolean hasNext, int size) {}
