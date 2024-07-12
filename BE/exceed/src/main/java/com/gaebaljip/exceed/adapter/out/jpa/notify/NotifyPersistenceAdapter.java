package com.gaebaljip.exceed.adapter.out.jpa.notify;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.domain.notify.NotifyEntity;
import com.gaebaljip.exceed.application.port.out.notify.NotifyPort;

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
