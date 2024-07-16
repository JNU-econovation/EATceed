package com.gaebaljip.exceed.application.port.in.notify;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gaebaljip.exceed.common.annotation.UseCase;

@UseCase
public interface ConnectEmitterUseCase {
    SseEmitter execute(final String memberId, final String lastEventId);
}
