package com.gaebaljip.exceed.common.event;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnExpression("${ableDomainEvent:true}")
public class EventPublisherAspect implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;
    private ThreadLocal<Boolean> appliedLocal = ThreadLocal.withInitial(() -> Boolean.FALSE);

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object handleEvent(ProceedingJoinPoint joinPoint) throws Throwable {

        boolean nested = appliedLocal.get();

        if (!nested) {
            appliedLocal.set(Boolean.TRUE);
            Events.setPublisher(publisher);
        }

        try {
            return joinPoint.proceed();
        } finally {
            if (!nested) {
                Events.reset();
                appliedLocal.remove();
            }
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.publisher = eventPublisher;
    }
}
