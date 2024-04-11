package com.gaebaljip.exceed.member.adapter.out.persistence.custom;

import static com.gaebaljip.exceed.member.adapter.out.persistence.QMemberEntity.memberEntity;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.QMemberEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomMemberRepositoryImpl implements CustomMemberRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberEntity> findByIdAndDate(Long memberId, LocalDate date) {
        MemberEntity result =
                queryFactory
                        .selectFrom(memberEntity)
                        .where(memberEntity.id.eq(memberId).and(checkDate(memberEntity, date)))
                        .fetchOne();
        return Optional.ofNullable(result);
    }

    private BooleanExpression checkDate(QMemberEntity memberEntity, LocalDate date) {
        StringExpression formattedDate =
                Expressions.stringTemplate(
                        "FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", memberEntity.createdDate);
        return formattedDate.eq(date.toString());
    }
}
