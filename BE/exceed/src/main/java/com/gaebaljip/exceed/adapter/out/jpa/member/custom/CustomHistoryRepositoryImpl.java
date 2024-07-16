package com.gaebaljip.exceed.adapter.out.jpa.member.custom;

import static com.gaebaljip.exceed.application.domain.member.QHistoryEntity.historyEntity;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.QHistoryEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomHistoryRepositoryImpl implements CustomHistoryRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public HistoryEntity findByMemberIdAndDate(Long memberId, LocalDateTime date) {
        return queryFactory
                .selectFrom(historyEntity)
                .where(historyEntity.id.eq(memberId).and(checkDate(historyEntity, date)))
                .orderBy(historyEntity.id.desc())
                .fetchFirst();
    }

    private BooleanExpression checkDate(QHistoryEntity historyEntity, LocalDateTime date) {
        return historyEntity.createdDate.before(date);
    }
}
