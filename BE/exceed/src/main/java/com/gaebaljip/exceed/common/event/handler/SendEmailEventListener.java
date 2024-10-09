package com.gaebaljip.exceed.common.event.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.thymeleaf.context.Context;

import com.gaebaljip.exceed.application.domain.member.Code;
import com.gaebaljip.exceed.application.port.out.member.CodePort;
import com.gaebaljip.exceed.application.port.out.member.EmailPort;
import com.gaebaljip.exceed.common.MailTemplate;
import com.gaebaljip.exceed.common.event.SendEmailEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SendEmailEventListener {
    private final EmailPort emailPort;
    private final CodePort codePort;

    @Value("${exceed.url}")
    private String URL;

    private Long expiredTime = 600000L;

    @TransactionalEventListener(classes = SendEmailEvent.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void handle(SendEmailEvent event) {
        codePort.saveWithExpiration(event.getEmail(), Code.create(), expiredTime);
        Context context = new Context();
        context.setVariable(
                MailTemplate.FIND_PASSWORD_MAIL_CONTEXT,
                URL + MailTemplate.REPLY_TO_FIND_PASSWORD_MAIL_URL);
        context.setVariable(MailTemplate.FIND_PASSWORD_EMAIL, "?email=" + event.getEmail());
        emailPort.sendEmail(
                event.getEmail(),
                MailTemplate.FIND_PASSWORD_TITLE,
                MailTemplate.FIND_PASSWORD_TEMPLATE,
                context);
    }
}
