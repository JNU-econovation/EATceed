package com.gaebaljip.exceed.member.adapter.out.persistence.custom;

import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.QHistoryEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.QMemberEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.gaebaljip.exceed.member.adapter.out.persistence.QHistoryEntity.*;


@Repository
@RequiredArgsConstructor
public class CustomHistoryRepositoryImpl implements CustomHistoryRepository{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public HistoryEntity findByMemberIdAndDate(Long memberId, LocalDate date) {
        return queryFactory
                .selectFrom(historyEntity)
                .where(historyEntity.id.eq(memberId).and(checkDate(historyEntity, date)))
                .orderBy(historyEntity.id.desc())
                .fetchFirst();
    }

    private BooleanExpression checkDate(QHistoryEntity historyEntity, LocalDate date) {
        return historyEntity.createdDate.before(date.atStartOfDay());
    }
}
