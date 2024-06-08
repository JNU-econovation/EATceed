package com.gaebaljip.exceed.infrastructure.sse.adapter.out;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotifyConverter {
    public Notify toNotify(NotifyEntity notifyEntity) {
        return Notify.builder()
                .content(notifyEntity.getContent())
                .url(notifyEntity.getUrl())
                .type(notifyEntity.getType().name())
                .build();
    }
}
