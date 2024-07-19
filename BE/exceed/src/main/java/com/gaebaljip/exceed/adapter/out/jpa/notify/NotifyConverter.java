package com.gaebaljip.exceed.adapter.out.jpa.notify;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.notify.Notify;
import com.gaebaljip.exceed.application.domain.notify.NotifyEntity;

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
