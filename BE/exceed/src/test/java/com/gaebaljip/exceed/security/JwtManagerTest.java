package com.gaebaljip.exceed.security;

import com.gaebaljip.exceed.member.domain.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.security.sasl.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JwtManager.class)
class JwtManagerTest {

    @Autowired
    private JwtManager jwtManager;

    @Test
    void validateAccessToken() throws AuthenticationException {
        String accessToken = jwtManager.generateAccessToken("test", MemberRole.GUEST.name());
        boolean validated = jwtManager.validateAccessToken(accessToken);
        assertTrue(validated);
    }

}