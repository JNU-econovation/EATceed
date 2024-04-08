package com.gaebaljip.exceed.member.adapter.out.persistence;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.gaebaljip.exceed.member.application.port.out.EmailPort;
import com.gaebaljip.exceed.member.exception.MailSendException;

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
    public boolean sendEmail(String to, String title, String template, Context context) {

        String html = htmlTemplateEngine.process(template, context);

        SendEmailRequest sendEmailRequest =
                SendEmailRequest.builder()
                        .destination(Destination.builder().toAddresses(to).build())
                        .message(newMessage(title, html))
                        .source(mailAddress)
                        .build();

        CompletableFuture<SendEmailResponse> completableFuture =
                sesAsyncClient.sendEmail(sendEmailRequest);

        SendEmailResponse sendEmailResponse;

        try {
            sendEmailResponse = completableFuture.join();
        } catch (Exception e) {
            e.printStackTrace();
            throw MailSendException.EXECPTION;
        }
        return sendEmailResponse.sdkHttpResponse().isSuccessful();
    }

    private Message newMessage(String subject, String html) {
        Content content = Content.builder().data(subject).build();
        return Message.builder()
                .subject(content)
                .body(Body.builder().html(builder -> builder.data(html)).build())
                .build();
    }
}
