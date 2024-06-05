package com.gaebaljip.exceed.infrastructure.sse.adapter.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.infrastructure.sse.application.port.out.NotifyPort;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotifyPersistenceAdapter implements NotifyPort {
    private final NotifyRepository notifyRepository;

    @Override
    public List<NotifyEntity> findByMemberEntity(MemberEntity memberEntity) {
        return notifyRepository.findByReceiver(memberEntity);
    }

    @Override
    public void deleteByAllByIdInQuery(List<Long> ids) {
        notifyRepository.deleteByAllByIdInQuery(ids);
    }

    @Override
    public NotifyEntity command(NotifyEntity notifyEntity) {
        return notifyRepository.save(notifyEntity);
    }
}
