package com.gaebaljip.exceed.security.service;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberRepository;
import com.gaebaljip.exceed.member.exception.MemberNotFoundException;
import com.gaebaljip.exceed.security.domain.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByLoginId(username).orElseThrow(MemberNotFoundException::new);
        return new MemberDetails(member);
    }
}
