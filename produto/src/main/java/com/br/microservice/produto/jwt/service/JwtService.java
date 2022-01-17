package com.br.microservice.produto.jwt.service;

import com.br.microservice.produto.config.exception.AuthenticationException;
import com.br.microservice.produto.jwt.dto.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Locale;

@Service
public class JwtService {

    private static final String EMPTY_SPACE = " ";

    private static final Integer  TOKEN_INDEX = 1;

    @Value("${app-config.secrets.api-secrets}")
    private String apiSecret;

    public void validateAuthorization(String token) {
        var accesToken = extractToken(token);
        try {

            var claims = Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes()))
                    .build()
                    .parseClaimsJws(accesToken)
                    .getBody();
            var user = JwtResponse.getUser(claims);
            if(ObjectUtils.isEmpty(user) || ObjectUtils.isEmpty(user.getId())){
                throw  new AuthenticationException("Usuario invalido");
            }
        } catch (Exception e) {
            throw new AuthenticationException("Token invalido.");
        }
    }

    private String extractToken(String token) {
        if (token == null || ObjectUtils.isEmpty(token)) {
            throw new AuthenticationException("Token informado invalido.");
        }
        if (token.contains(EMPTY_SPACE)) {
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }

        return token;
    }
}
