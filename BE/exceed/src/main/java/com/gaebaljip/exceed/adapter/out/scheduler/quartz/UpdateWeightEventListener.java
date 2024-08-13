package com.gaebaljip.exceed.adapter.out.scheduler.quartz;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.gaebaljip.exceed.adapter.out.scheduler.quartz.job.UpdateWeightEmitterJob;
import com.gaebaljip.exceed.application.domain.notify.NotificationType;
import com.gaebaljip.exceed.common.event.UpdateWeightEvent;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class UpdateWeightEventListener {
    @TransactionalEventListener(
            classes = UpdateWeightEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void handle(UpdateWeightEvent event) {
        Long memberId = event.getMemberId();
        String url = event.getUrl();
        JobDetail jobDetail = createJobDetail(memberId, url);
        Trigger trigger = createTrigger(event, jobDetail);
        createSchedule(jobDetail, trigger);
    }

    private void createSchedule(JobDetail jobDetail, Trigger trigger) {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            log.info(">>>>>>>>>>>>>>>>> SSE 전송 스케줄링 시작");
            scheduler.scheduleJob(jobDetail, trigger);
            log.info(">>>>>>>>>>>>>>>>> SSE 전송 스케줄링 등록");
        } catch (SchedulerException e) {
            log.error(">>>>>>>>>>>>>>>>> SSE 전송 스케줄링 실패");
        }
    }

    private JobDetail createJobDetail(Long memberId, String url) {
        String content = "몸무게 수정하신지 7일이 지났습니다. 몸무게를 수정해주세요";
        NotificationType type = NotificationType.NOTICE;
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("memberId", memberId);
        jobDataMap.put("url", url);
        jobDataMap.put("content", content);
        jobDataMap.put("type", type);
        return JobBuilder.newJob(UpdateWeightEmitterJob.class)
                .withIdentity("UPDATE_WEIGHT_JOB", "group1")
                .setJobData(jobDataMap)
                .build();
    }

    private Trigger createTrigger(UpdateWeightEvent event, JobDetail jobDetail) {
        LocalDateTime triggerDateTime = event.getLocalDateTime().plusMinutes(1);
        Date triggerDate = Date.from(triggerDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());
        TriggerBuilder<Trigger> triggerTriggerBuilder = TriggerBuilder.newTrigger();
        triggerTriggerBuilder.withIdentity("UPDATE_WEIGHT_TRIGGER", "group1");
        triggerTriggerBuilder.startAt(triggerDate);
        triggerTriggerBuilder.forJob(jobDetail);
        return triggerTriggerBuilder.build();
    }
}
