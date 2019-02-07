package com.bbahaida.dataqualitymanagement.security;

public interface SecurityConstants {
    String SECRET = "bbahaida";
    long EXPIRATION_TIME = 864_000_000;
    String JWT_HEADER_STRING = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
}
