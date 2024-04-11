package com.gaebaljip.exceed.member.adapter.out.persistence;

import com.gaebaljip.exceed.member.adapter.out.persistence.custom.CustomHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long>, CustomHistoryRepository {}
