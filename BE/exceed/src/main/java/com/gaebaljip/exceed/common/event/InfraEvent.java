package com.gaebaljip.exceed.common.event;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class InfraEvent {
    private final LocalDateTime publishAt;

    public InfraEvent() {
        this.publishAt = LocalDateTime.now();
    }
}
