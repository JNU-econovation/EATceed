package com.gaebaljip.exceed.infrastructure.sse.application;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gaebaljip.exceed.infrastructure.sse.adapter.out.*;
import com.gaebaljip.exceed.infrastructure.sse.application.port.in.SendEmitterUseCase;
import com.gaebaljip.exceed.infrastructure.sse.application.port.out.NotifyPort;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class sendEmitterService implements SendEmitterUseCase {
    private final EmitterRepository emitterRepository;
    private final NotifyPort notifyPort;
    private final NotifyConverter notifyConverter;

    // 놓친 이벤트를 전부 재전송 하는 것이 아닌 최근 것 하나만 재전송
    @Override
    @Transactional(readOnly = true)
    public void sendLostData(
            String lastEventId, String memberId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches =
                emitterRepository.findAllEventCacheStartWithByMemberId(memberId);
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .sorted(
                        Comparator.comparing(
                                        entry ->
                                                splitEventIdToTime(
                                                        ((Map.Entry<String, Object>) entry)
                                                                .getKey()))
                                .reversed())
                .limit(1)
                .forEach(
                        entry ->
                                sendNotification(
                                        emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    @Override
    @Transactional
    public void sendNotification(
            SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event().id(eventId).name("sse").data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            emitter.completeWithError(exception);
        }
    }

    @Override
    public void send(MemberEntity receiver, String content, String url, NotificationType type) {
        NotifyEntity notifyEntity = NotifyEntity.createNotify(receiver, content, url, type);
        Notify notify = notifyConverter.toNotify(notifyEntity);
        String eventId = receiver.getId() + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters =
                emitterRepository.findAllEmitterStartWithByMemberId(receiver.getId().toString());
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notify);
                    sendNotification(emitter, eventId, key, notify);
                });
    }

    private Long splitEventIdToTime(String eventId) {
        return Long.parseLong(eventId.split("_")[1]);
    }
}
