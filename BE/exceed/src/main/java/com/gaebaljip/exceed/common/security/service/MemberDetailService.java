package com.gaebaljip.exceed.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.adapter.out.jpa.member.MemberRepository;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.exception.member.MemberNotFoundException;
import com.gaebaljip.exceed.common.security.domain.MemberDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        MemberEntity member =
                memberRepository
                        .findById(Long.valueOf(memberId))
                        .orElseThrow(() -> MemberNotFoundException.EXECPTION);
        return new MemberDetails(member);
    }
}
