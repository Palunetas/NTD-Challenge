package com.ntd.challenge.security;

import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

public class TokenJwtConfig {

    public static final String CONTENT_TYPE = "application/json";
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
}
