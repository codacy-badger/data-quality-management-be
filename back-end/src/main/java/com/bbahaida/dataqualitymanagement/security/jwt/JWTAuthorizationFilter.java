package com.bbahaida.dataqualitymanagement.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bbahaida.dataqualitymanagement.security.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "" +
                "Origin, " +
                "Accept, " +
                "X-Requested-With, " +
                "Content-Type" +
                "Access-Control-Request-Method, " +
                "Access-Control-Request-Headers, " +
                "authorization");
        response.addHeader("Access-Control-Expose-Headers", "" +
                "Access-Control-Allow-Origin, " +
                "Access-Control-Allow-Credentials, " +
                "authorization");
        if ( request.getMethod().equals("OPTIONS") ) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            String jwtToken = request.getHeader(SecurityConstants.JWT_HEADER_STRING);

            if ( jwtToken == null || !jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX) ){
                filterChain.doFilter(request,response);
                return;
            }
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityConstants.SECRET)).build();
            DecodedJWT decodedJWT = verifier.verify(jwtToken.replace(SecurityConstants.TOKEN_PREFIX, ""));
            String username = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
            logger.info(username+ " was logged in with roles "+roles);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);

        }
    }
}
