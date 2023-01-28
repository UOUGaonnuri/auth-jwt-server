package com.gaounuri.authjwtserver.utils;

import com.gaounuri.authjwtserver.user.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtUtils {

    private UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.security.key}")
    private String SECRET_KEY;

    private SignatureAlgorithm SIGNATURE_ALG = SignatureAlgorithm.HS256;

    public static final String REFRESH_TOKEN_NAME = "refresh_token";
    public static final Long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L;
    public static final Long REFRESH_TOKEN_VALID_TIME = 30 * 24 * 60 * 60 * 100L;

    public Key getSigningkey(String secretKey){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractAllClaims(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningkey(SECRET_KEY))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public String createToken(String userEmail, Long validTime) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("userEmail", userEmail);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validTime))
                .signWith(getSigningkey(SECRET_KEY), SIGNATURE_ALG)
                .compact();
    }

    public String getUserEmail(String token){
        return extractAllClaims(token).get("uerEmail", String.class);
    }

    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null){
            return token.substring("Bearer ".length());
        } else {
            return "";
        }
    }

    public boolean validateToken(String jwtToken){
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getSigningkey(SECRET_KEY)).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());

        } catch (Exception e){
            return false;
        }
    }

    public Long getExpiration(String jwtToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(getSigningkey(SECRET_KEY)).build()
                .parseClaimsJws(jwtToken).getBody().getExpiration();
        long now = System.currentTimeMillis();
        return expiration.getTime() - now;
    }


}
