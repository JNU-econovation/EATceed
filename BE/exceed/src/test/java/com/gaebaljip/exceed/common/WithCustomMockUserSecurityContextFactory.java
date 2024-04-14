package com.gaebaljip.exceed.common;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.security.domain.CustomUsernamePasswordAuthenticationToken;
import com.gaebaljip.exceed.security.domain.MemberDetails;

public class WithCustomMockUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        Long memberId = annotation.memberId();
        MemberEntity memberEntity = MemberEntity.builder().id(memberId).build();
        MemberDetails memberDetails = new MemberDetails(memberEntity);
        Authentication authentication =
                new CustomUsernamePasswordAuthenticationToken(
                        memberDetails,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_MEMBER")),
                        memberId);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        return context;
    }
}
