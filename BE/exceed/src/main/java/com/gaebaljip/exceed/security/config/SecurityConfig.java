package com.gaebaljip.exceed.security.config;

import com.gaebaljip.exceed.security.filter.JwtAuthenticationFilter;
import com.gaebaljip.exceed.security.domain.JwtManager;
import com.gaebaljip.exceed.security.domain.JwtResolver;
import com.gaebaljip.exceed.security.service.MemberDetailService;
import com.gaebaljip.exceed.security.exception.JwtAccessDeniedHandler;
import com.gaebaljip.exceed.security.exception.JwtAuthenticationPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtManager jwtManager;
    private final JwtResolver jwtResolver;
    private final MemberDetailService memberDetailService;
    private final JwtAuthenticationPoint jwtAuthenticationPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // httpBasic, csrf, formLogin, rememberMe, logout, session disable
        http.httpBasic()
                .disable()

                .csrf()
                .disable()

                .formLogin()
                .disable()

                .rememberMe()
                .disable()

                .logout()
                .disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .cors()
                .configurationSource(corsConfigurationSource())

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationPoint)

                .and()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin();

        // 요청에 대한 권한 설정
        http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(HttpMethod.POST, "/v1/members-guest").permitAll()
                .anyRequest().authenticated();


        // jwt filter 설정
        http.addFilterBefore(new JwtAuthenticationFilter(jwtManager, jwtResolver, memberDetailService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://10.0.2.2:8080"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/docs/api-doc.html");
    }


}
