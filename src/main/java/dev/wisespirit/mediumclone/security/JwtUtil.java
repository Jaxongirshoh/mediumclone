package dev.wisespirit.mediumclone.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final Key signingKey;
    private final long accessTokenValidityInSeconds;
    private final long refreshTokenValidityInSeconds;
    private final String issuer;
    private final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);

    public JwtUtil(@Value("${jwt.secret.key}") String secretKey,
                   @Value("${jwt.access-token.ttl}") long accessTokenValidityInSeconds,
                   @Value("${jwt.refresh-token.ttl}") long refreshTokenValidityInSeconds,
                   @Value("${jwt.issuer}") String issuer) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
        this.issuer = issuer;
    }

    public String generateAccessToken(String email, Map<String,Object> claims){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(issuer)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidityInSeconds * 100))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .addClaims(claims)
                .compact();
    }

    public String generateRefreshToken(String email,Map<String ,Object> claims){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(issuer)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidityInSeconds * 100))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .addClaims(claims)
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmail(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
    public boolean isValid(String token){
        try {
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        }catch (Exception e){
            LOG.error("token is not valid");
        }
        return false;
    }
}
