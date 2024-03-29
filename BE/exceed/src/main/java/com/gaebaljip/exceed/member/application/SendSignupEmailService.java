package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.common.MailTemplate;
import com.gaebaljip.exceed.member.application.port.in.SendEmailCommand;
import com.gaebaljip.exceed.member.application.port.in.SendEmailUsecase;
import com.gaebaljip.exceed.member.application.port.out.EmailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Qualifier("SendSignupEmailService")
public class SendSignupEmailService implements SendEmailUsecase {

    private final EmailPort emailPort;
    @Override
    public void execute(SendEmailCommand sendEmailCommand) {
        Context context = new Context();
        context.setVariable(MailTemplate.SIGN_UP_MAIL_CONTEXT,MailTemplate.REPLY_TO_SIGN_UP_MAIL_URL);
        emailPort.sendEmail(
                sendEmailCommand.email(),
                MailTemplate.SIGN_UP_TITLE,
                MailTemplate.SIGN_UP_TEMPLATE,
                context);
    }
}
