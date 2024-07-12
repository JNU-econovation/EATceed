package com.gaebaljip.exceed.adapter.in.notify;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gaebaljip.exceed.application.port.in.notify.ConnectEmitterUseCase;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "access-token")
@Tag(name = "[SSE connection]")
public class EmitterController {
    private final ConnectEmitterUseCase connectEmitterUseCase;

    @GetMapping(value = "/emitter/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
                    String lastEventId,
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        return connectEmitterUseCase.execute(memberId.toString(), lastEventId);
    }
}
