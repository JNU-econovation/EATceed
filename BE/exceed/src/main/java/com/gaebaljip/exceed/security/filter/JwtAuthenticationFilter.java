package com.gaebaljip.exceed.security.filter;

import com.gaebaljip.exceed.security.domain.CustomUsernamePasswordAuthenticationToken;
import com.gaebaljip.exceed.security.domain.JwtManager;
import com.gaebaljip.exceed.security.domain.JwtResolver;
import com.gaebaljip.exceed.security.domain.MemberDetails;
import com.gaebaljip.exceed.security.service.MemberDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final JwtResolver jwtResolver;
    private final MemberDetailService memberDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtResolver.extractToken(bearerToken);
        try {
            if (jwtManager.validateAccessToken(accessToken,request)) {
                MemberDetails memberDetails = (MemberDetails) memberDetailService.loadUserByUsername(jwtResolver.getLoginIdFromToken(accessToken));
                Authentication authentication = new CustomUsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities(), jwtResolver.getMemberIdFromToken(accessToken));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("user ={}, uri ={}, method = {}, time={}, message={}",
                        request.getRemoteUser(), request.getRequestURL(), request.getMethod(), LocalDateTime.now(), "인증 성공");
            }
        } catch (Exception e) {
            log.error("method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(), request.getRequestURL(), LocalDateTime.now(),e.getMessage());
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }
}
