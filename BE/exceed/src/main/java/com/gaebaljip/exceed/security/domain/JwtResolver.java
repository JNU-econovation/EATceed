package com.gaebaljip.exceed.security.domain;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gaebaljip.exceed.security.AuthConstants;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtResolver {
    private final Key key;

    public JwtResolver(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getMemberIdFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String Token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(Token).getBody();
    }

    public String extractToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith(AuthConstants.TOKEN_TYPE.getValue())) {
            return bearerToken.replace(AuthConstants.TOKEN_TYPE.getValue(), "");
        }
        return null;
    }
}
