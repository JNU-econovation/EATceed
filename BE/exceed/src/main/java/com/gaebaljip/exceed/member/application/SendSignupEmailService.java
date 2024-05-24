package com.gaebaljip.exceed.member.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.common.annotation.EventPublisherStatus;
import com.gaebaljip.exceed.common.event.Events;
import com.gaebaljip.exceed.common.event.SendEmailEvent;
import com.gaebaljip.exceed.member.application.port.in.SendEmailCommand;
import com.gaebaljip.exceed.member.application.port.in.SendEmailUsecase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Qualifier("SendSignupEmailService")
public class SendSignupEmailService implements SendEmailUsecase {
    @Override
    @Transactional
    @EventPublisherStatus
    public void execute(SendEmailCommand sendEmailCommand) {
        Events.raise(SendEmailEvent.from(sendEmailCommand.email()));
    }
}
