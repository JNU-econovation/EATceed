package com.gaebaljip.exceed.common.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateWeightEvent extends InfraEvent {
    private Long memberId;
    private String url;
    private LocalDateTime localDateTime;

    public static UpdateWeightEvent from(Long memberId, String url, LocalDateTime localDateTime) {
        return new UpdateWeightEvent(memberId, url, localDateTime);
    }
}
