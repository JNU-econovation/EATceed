package com.gaebaljip.exceed.member.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Boolean existsByEmail(String email);

    @Query("select m.checked from MemberEntity m where m.email = :email")
    Boolean findCheckedByEmail(@Param("email") String email);

    Optional<MemberEntity> findByEmail(String email);
}
