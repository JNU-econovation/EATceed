package com.gaebaljip.exceed.common.event.handler;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.thymeleaf.context.Context;

import com.gaebaljip.exceed.common.Encryption;
import com.gaebaljip.exceed.common.MailTemplate;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.event.SendEmailEvent;
import com.gaebaljip.exceed.member.application.port.out.EmailPort;
import com.gaebaljip.exceed.member.application.port.out.TimeOutPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SendEmailEventListener {

    private final EmailPort emailPort;
    private final Encryption encryption;
    private final TimeOutPort timeOutPort;

    @Value("${exceed.url}")
    private String URL;

    @TransactionalEventListener(classes = SendEmailEvent.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Timer
    @Async
    public void handle(SendEmailEvent event) {
        String uuid = UUID.randomUUID().toString();
        timeOutPort.command(event.getEmail(), uuid);

        String code = encryption.encrypt(uuid);
        Context context = new Context();
        context.setVariable(
                MailTemplate.SIGN_UP_MAIL_CONTEXT, URL + MailTemplate.REPLY_TO_SIGN_UP_MAIL_URL);
        context.setVariable(MailTemplate.SIGN_UP_CHECK_COOD, "&code=" + code);
        context.setVariable(MailTemplate.SIGN_UP_EMAIL, "?email=" + event.getEmail());
        emailPort.sendEmail(
                event.getEmail(),
                MailTemplate.SIGN_UP_TITLE,
                MailTemplate.SIGN_UP_TEMPLATE,
                context);
    }
}
