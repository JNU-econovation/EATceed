package com.gaebaljip.exceed.application.port.out.member;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;

@Component
public interface MemberPort {

    MemberEntity query(Long memberId);

    Member query(Long memberId, LocalDateTime date);

    MemberEntity command(MemberEntity memberEntity);

    Boolean isChecked(String email);

    MemberEntity findMemberByEmail(String email);

    MemberEntity findCheckedMemberByEmail(String email);

    Optional<MemberEntity> findByIdAndDate(Long memberId, LocalDateTime date);

    Boolean existsByEmail(String email);

    void delete(MemberEntity memberEntity);
}
