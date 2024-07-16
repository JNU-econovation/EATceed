package com.gaebaljip.exceed.dto;

import java.util.List;

import com.gaebaljip.exceed.adapter.out.jpa.food.FoodEntity;

public record PageableFoodDTO(List<FoodEntity> foodEntities, Boolean hasNext, int size) {}
