package com.portafolio.gtareas.config.jwt;

import com.portafolio.gtareas.database.models.Usuario;
import com.portafolio.gtareas.services.exception.ApiException;
import com.portafolio.gtareas.services.messages.ApiMessages;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String KEY_SECRET = "51F38F211D6443E4B2FB0596B261AE8127856A8CC33645A99B4D69D1BEDC3007";
    private static final long TOKEN_EXPIRATION = 1000 * 60 * 60 * 24;


    public String generateToken(Usuario usuario) {
        return getToken(new HashMap<>(), usuario);
    }

    private String getToken(Map<String, Object> objectObjectHashMap, Usuario usuario) {
        return Jwts.builder()
                .setClaims(objectObjectHashMap)
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(getSingInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String getSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public Boolean isTokenValid(String token, UserDetails userDetails) {
        return getSubject(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Key getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(KEY_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSingInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new ApiException(ApiMessages.ERROR_JWT_MAL_FORMADO_EXPIRADO);
        }
    }
}
