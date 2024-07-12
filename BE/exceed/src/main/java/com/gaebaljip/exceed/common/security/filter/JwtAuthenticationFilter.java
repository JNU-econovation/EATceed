package com.gaebaljip.exceed.common.security.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gaebaljip.exceed.common.security.domain.JwtManager;
import com.gaebaljip.exceed.common.security.domain.JwtResolver;
import com.gaebaljip.exceed.common.security.domain.MemberDetails;
import com.gaebaljip.exceed.common.security.service.MemberDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final JwtResolver jwtResolver;
    private final MemberDetailService memberDetailService;
    private final List<String> excludeUrl = List.of("/actuator", "/v1/health");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("bearerToken : {}", bearerToken);
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = jwtResolver.extractToken(bearerToken);
        try {
            if (jwtManager.validateAccessToken(accessToken, request)) {
                MemberDetails memberDetails =
                        (MemberDetails)
                                memberDetailService.loadUserByUsername(
                                        jwtResolver.getMemberIdFromToken(accessToken));
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                memberDetails, null, memberDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info(
                        "user ={}, uri ={}, method = {}, time={}, message={}",
                        request.getRemoteUser(),
                        request.getRequestURL(),
                        request.getMethod(),
                        LocalDateTime.now(),
                        "인증 성공");
            }
        } catch (Exception e) {
            log.error(
                    "method ={}, URL = {}, time={}, errorMessage={}",
                    request.getMethod(),
                    request.getRequestURL(),
                    LocalDateTime.now(),
                    e.getMessage());
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean flag = false;
        for (String url : excludeUrl) {
            if (path.startsWith(url)) {
                flag = true;
            }
        }
        return flag;
    }
}
