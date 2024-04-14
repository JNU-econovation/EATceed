package com.gaebaljip.exceed.member.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaebaljip.exceed.member.adapter.out.persistence.custom.CustomHistoryRepository;

public interface HistoryRepository
        extends JpaRepository<HistoryEntity, Long>, CustomHistoryRepository {}
