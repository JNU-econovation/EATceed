package com.gaebaljip.exceed.dto.response;

import java.util.List;

import com.gaebaljip.exceed.food.adapter.out.FoodEntity;

public record PageableFood(List<FoodEntity> foodEntities, Boolean hasNext, int size) {}
