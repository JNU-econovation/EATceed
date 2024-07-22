package com.gaebaljip.exceed.adapter.out.jpa.member.custom;

import static com.gaebaljip.exceed.application.domain.member.QMemberEntity.memberEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.domain.member.QMemberEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomMemberRepositoryImpl implements CustomMemberRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberEntity> findMemberBeforeDate(Long memberId, LocalDateTime date) {
        MemberEntity result =
                queryFactory
                        .selectFrom(memberEntity)
                        .where(memberEntity.id.eq(memberId).and(checkDate(memberEntity, date)))
                        .fetchOne();
        return Optional.ofNullable(result);
    }

    private BooleanExpression checkDate(QMemberEntity memberEntity, LocalDateTime date) {
        return memberEntity.updatedDate.before(date);
    }
}
