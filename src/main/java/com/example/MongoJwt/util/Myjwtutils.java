package com.example.MongoJwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class Myjwtutils {
    private String secretKey = "nanda";

    public String getusernamefromToken(String token) {
        String claimfromToken = getClaimfromToken(token, Claims::getSubject);
        return claimfromToken;
    }

    public <T> T getClaimfromToken(String token, Function<Claims, T> claimresolver) {
        final Claims claims = getallClaimsFromToken(token);

        return claimresolver.apply(claims);
    }

    public Claims getallClaimsFromToken(String token) {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return body;
    }

    public boolean istokenExpired(String token) {
        Date expirationtoken = getClaimfromToken(token, Claims::getExpiration);
        return expirationtoken.before(new Date());

    }

    public Date getexpirationdatefromtoken(String token) {
        return getClaimfromToken(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String token1 = getusernamefromToken(token);
        return (userDetails.getUsername() == token1 && !istokenExpired(token));
    }
}
