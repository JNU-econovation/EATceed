package com.gaebaljip.exceed.adapter.out.jpa.member.custom;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;

@Repository
public interface CustomMemberRepository {
    Optional<MemberEntity> findMemberBeforeDate(Long memberId, LocalDateTime date);
}
