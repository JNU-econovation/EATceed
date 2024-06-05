package com.gaebaljip.exceed.infrastructure.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.gaebaljip.exceed.infrastructure.sse.adapter.out.NotificationType;
import com.gaebaljip.exceed.infrastructure.sse.application.port.in.SendEmitterUseCase;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
// Application Context에 Bean 등록되있는 것을 DI 하기 위해 QuartzJobBean을 상속함
public class UpdateWeightEmitterJob extends QuartzJobBean {
    private final SendEmitterUseCase sendEmitterUseCase;
    private final MemberPort memberPort;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        Long memberId = jobDataMap.getLong("memberId");
        MemberEntity receiver = memberPort.query(memberId);
        String url = jobDataMap.getString("url");
        String content = jobDataMap.getString("content");
        NotificationType notificationType = (NotificationType) jobDataMap.get("type");
        sendEmitterUseCase.send(receiver, content, url, notificationType);
    }
}
