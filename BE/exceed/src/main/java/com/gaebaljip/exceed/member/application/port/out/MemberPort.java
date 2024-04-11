package com.gaebaljip.exceed.member.application.port.out;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

@Component
public interface MemberPort {

    MemberEntity query(Long memberId);

    MemberEntity command(MemberEntity memberEntity);

    Boolean findEmailOrChecked(String email);

    MemberEntity findMemberByEmail(String email);

    MemberEntity findCheckedMemberByEmail(String email);

    Optional<MemberEntity> findByIdAndDate(Long memberId, LocalDate date);
}
