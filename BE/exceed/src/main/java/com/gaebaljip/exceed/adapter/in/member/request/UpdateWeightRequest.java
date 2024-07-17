package com.gaebaljip.exceed.adapter.in.member.request;

import lombok.Builder;

public record UpdateWeightRequest(Double weight, Double targetWeight) {
    @Builder
    public UpdateWeightRequest {}
}
