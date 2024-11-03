package com.crispy.crispy_be_challenge_ejona_aliaj.security.jwt;

import com.crispy.crispy_be_challenge_ejona_aliaj.exception.JWTVerifyException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * TokenProvider is responsible for handling JWT (JSON Web Token) operations
 * such as token generation, extraction of claims, and token validation.
 */
@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";

    private static final String INVALID_JWT_TOKEN = "Invalid JWT token.";

    private final String base64Secret;

    private final long tokenValidityInMilliseconds;

    private final JwtParser jwtParser;

    private final Key key;

    public TokenProvider(@Value("${jwt.base64-secret}") String base64Secret, @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.base64Secret = base64Secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.key = getSignKey();
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    /**
     * Creates a signing key from the base64 encoded secret.
     *
     * @return a Key object for signing the JWT.
     */
    private Key getSignKey() {
        // Decode the base64 encoded secret key and return a Key object
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities).signWith(key, SignatureAlgorithm.HS512).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(validity).compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = extractAllClaims(token);
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }


    /**
     * Extracts all claims from the JWT token.
     *
     * @param token token
     * @return Claims object containing all claims.
     */
    private Claims extractAllClaims(String token) {
        // Parse and return all claims from the token
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * Validates the JWT token against the UserDetails.
     *
     * @param authToken authentication token
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SecurityException |
                 SignatureException e) {
            log.trace(INVALID_JWT_TOKEN, e);
            throw new JWTVerifyException(INVALID_JWT_TOKEN, null);
        } catch (IllegalArgumentException e) {
            log.error("Token validation error {}", e.getMessage());
        }
        return false;
    }
}
