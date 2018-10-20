package com.efiab.springdockerjib.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.MacProvider
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.stereotype.Service

import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static java.util.Collections.emptyList

@Service
class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 3_600_000; // 1 hr
    //creates a 512-bit secure-random key (the default):
    static final SecretKey SECRET = MacProvider.generateKey();

    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse res, String username) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SECRET, SignatureAlgorithm.HS512)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        res.flushBuffer()
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "").trim())
                    .getBody()
                    .getSubject();

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }
        return null;
    }

}
