package com.gaebaljip.exceed.infrastructure.sse.application.port.in;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gaebaljip.exceed.common.annotation.UseCase;

@UseCase
public interface ConnectEmitterUseCase {
    SseEmitter connect(final String memberId, final String lastEventId);
}
