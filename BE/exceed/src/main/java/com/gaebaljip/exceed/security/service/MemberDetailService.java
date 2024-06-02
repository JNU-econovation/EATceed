package com.gaebaljip.exceed.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberRepository;
import com.gaebaljip.exceed.member.exception.MemberNotFoundException;
import com.gaebaljip.exceed.security.domain.MemberDetails;

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
