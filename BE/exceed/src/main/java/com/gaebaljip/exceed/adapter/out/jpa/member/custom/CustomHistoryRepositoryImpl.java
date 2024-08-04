package com.gaebaljip.exceed.adapter.out.jpa.member.custom;

import static com.gaebaljip.exceed.application.domain.member.QHistoryEntity.historyEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<HistoryEntity> findRecentFutureMember(Long memberId, LocalDateTime date) {
        HistoryEntity result =
                queryFactory
                        .selectFrom(historyEntity)
                        .where(
                                historyEntity
                                        .memberEntity
                                        .id
                                        .eq(memberId)
                                        .and(checkFutureDate(historyEntity, date)))
                        .orderBy(historyEntity.id.asc())
                        .fetchFirst();
        return Optional.ofNullable(result);
    }

    private BooleanExpression checkFutureDate(QHistoryEntity historyEntity, LocalDateTime date) {
        return historyEntity.createdDate.after(date);
    }

    @Override
    public List<HistoryEntity> findMembersBetweenDate(
            Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return queryFactory
                .selectFrom(historyEntity)
                .where(
                        historyEntity
                                .memberEntity
                                .id
                                .eq(memberId)
                                .and(historyEntity.createdDate.between(startDateTime, endDateTime)))
                .fetch();
    }
}
