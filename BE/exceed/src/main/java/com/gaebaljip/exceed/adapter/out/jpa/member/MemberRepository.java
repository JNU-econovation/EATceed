package com.gaebaljip.exceed.adapter.out.jpa.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gaebaljip.exceed.adapter.out.jpa.member.custom.CustomMemberRepository;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;

public interface MemberRepository
        extends JpaRepository<MemberEntity, Long>, CustomMemberRepository {

    Boolean existsByEmail(String email);

    @Query("select m.checked from MemberEntity m where m.email = :email")
    Boolean findCheckedByEmail(@Param("email") String email);

    Optional<MemberEntity> findByEmail(String email);
}
