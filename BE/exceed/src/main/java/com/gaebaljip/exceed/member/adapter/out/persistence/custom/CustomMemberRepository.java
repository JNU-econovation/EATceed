package com.gaebaljip.exceed.member.adapter.out.persistence.custom;

import java.time.LocalDate;
import java.util.Optional;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

@Repository
public interface CustomMemberRepository {
    Optional<MemberEntity> findByIdAndDate(Long memberId, LocalDate date);
}
