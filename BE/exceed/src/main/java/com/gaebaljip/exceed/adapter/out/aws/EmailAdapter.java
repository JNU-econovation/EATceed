package com.gaebaljip.exceed.adapter.out.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.gaebaljip.exceed.application.port.out.member.EmailPort;
import com.gaebaljip.exceed.common.annotation.Timer;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ses.SesAsyncClient;
import software.amazon.awssdk.services.ses.model.*;

@Component
@RequiredArgsConstructor
public class EmailAdapter implements EmailPort {

    @Value("${cloud.aws.ses.mail-address}")
    private String mailAddress;

    private final SesAsyncClient sesAsyncClient;
    private final SpringTemplateEngine htmlTemplateEngine;

    @Override
    @Timer
    public void sendEmail(String to, String title, String template, Context context) {

        String html = htmlTemplateEngine.process(template, context);

        SendEmailRequest sendEmailRequest =
                SendEmailRequest.builder()
                        .destination(Destination.builder().toAddresses(to).build())
                        .message(newMessage(title, html))
                        .source(mailAddress)
                        .build();

        sesAsyncClient.sendEmail(sendEmailRequest);
    }

    private Message newMessage(String subject, String html) {
        Content content = Content.builder().data(subject).build();
        return Message.builder()
                .subject(content)
                .body(Body.builder().html(builder -> builder.data(html)).build())
                .build();
    }
}
