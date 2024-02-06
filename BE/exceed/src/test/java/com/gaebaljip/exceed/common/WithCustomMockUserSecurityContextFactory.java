package com.gaebaljip.exceed.common;

import com.gaebaljip.exceed.security.domain.CustomUsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockGuestUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockGuestUser annotation) {
        Long memberId = annotation.memberId();
        Authentication authentication = new CustomUsernamePasswordAuthenticationToken(null, null, List.of(new SimpleGrantedAuthority("ROLE_GUEST")), memberId);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        return context;
    }
}
