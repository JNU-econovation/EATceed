package com.gaebaljip.exceed.infrastructure.sse.application;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gaebaljip.exceed.infrastructure.sse.adapter.out.EmitterRepository;
import com.gaebaljip.exceed.infrastructure.sse.adapter.out.NotifyRepository;
import com.gaebaljip.exceed.infrastructure.sse.application.port.in.ConnectEmitterUseCase;
import com.gaebaljip.exceed.infrastructure.sse.application.port.in.SendEmitterUseCase;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberPersistenceAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConnectEmitterService implements ConnectEmitterUseCase {
    private final EmitterRepository emitterRepository;
    private final NotifyRepository notifyRepository;
    private final MemberPersistenceAdapter memberPersistenceAdapter;
    private final SendEmitterUseCase sendEmitterUseCase;
    private final Long CONNECT_TIMEOUT = 1000L * 60 * 60;

    @Override
    public SseEmitter connect(final String memberId, final String lastEventId) {
        String eventId = memberId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(eventId, new SseEmitter(CONNECT_TIMEOUT));
        // 이벤트를 정상적으로 보냈을 때
        emitter.onCompletion(() -> emitterRepository.deleteById(eventId));
        // SSE가 timeout 되었을 때
        emitter.onTimeout(() -> emitterRepository.deleteById(eventId));
        // 503 에러를 방지하기 위한 더미 데이터 전송
        sendEmitterUseCase.sendNotification(
                emitter, eventId, eventId, "EventStream Created. [memberId=" + memberId + "]");
        // 클라이언트가 놓친 이벤트가 있다면 재전송
        if (!lastEventId.isEmpty()) {
            sendEmitterUseCase.sendLostData(lastEventId, memberId, eventId, emitter);
        }
        return emitter;
    }
}
