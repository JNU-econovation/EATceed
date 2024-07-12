package com.gaebaljip.exceed.application.port.in.notify;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.domain.notify.NotificationType;
import com.gaebaljip.exceed.common.annotation.UseCase;

@UseCase
public interface SendEmitterUseCase {
    void sendLostData(String lastEventId, String memberId, String emitterId, SseEmitter emitter);

    void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data);

    void send(MemberEntity receiver, String content, String url, NotificationType type);
}
