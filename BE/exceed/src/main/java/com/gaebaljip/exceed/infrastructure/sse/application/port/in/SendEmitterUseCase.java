package com.gaebaljip.exceed.infrastructure.sse.application.port.in;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gaebaljip.exceed.common.annotation.UseCase;
import com.gaebaljip.exceed.infrastructure.sse.adapter.out.NotificationType;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

@UseCase
public interface SendEmitterUseCase {
    void sendLostData(String lastEventId, String memberId, String emitterId, SseEmitter emitter);

    void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data);

    void send(MemberEntity receiver, String content, String url, NotificationType type);
}
