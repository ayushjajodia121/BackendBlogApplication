//package com.psi.ipg.account.util;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.psi.interview_assessment_app.entity.User;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class JwtUtil {
//
//    @Value("${jwt.issuer}")
//    private String issuer;
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expiry.sec}")
//    private Long expiryInSec;
//
//    public String extractUsername(String token) {
//        return decodedJWT(token).getSubject();
//    }
//
//    public Date extractExpiration(String token) {
//        return decodedJWT(token).getExpiresAt();
//    }
//
//    public String generateToken(User user, List<String> authorities) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", user.getUserId());
//        claims.put("email", user.getEmail());
//        claims.put("firstName", user.getFirstName());
//        claims.put("lastName", user.getLastName());
//        claims.put("departmentId", user.getDepartment().getDepartmentId());
//        claims.put("permission", authorities);
//        return createToken(claims, user.getEmail());
//    }
//
//    private String createToken(Map<String, Object> claims, String subject) {
//        long tokenExpiry = System.currentTimeMillis() + (expiryInSec * 1000);
//        return JWT.create().withPayload(claims).withSubject(subject).withIssuedAt(new Date(System.currentTimeMillis())).withExpiresAt(new Date(tokenExpiry)).withIssuer(issuer).sign(Algorithm.HMAC256(secret));
//    }
//
//    public Set<GrantedAuthority> getAuthorities(String token) {
//        DecodedJWT decodedJWT = decodedJWT(token);
//
//        HashSet<GrantedAuthority> authorities = new HashSet<>();
//        final Map<String, Claim> claimMap = decodedJWT.getClaims();
//        List<String> permissions = claimMap.get("permission").as(List.class);
//        permissions.forEach(p -> {
//            GrantedAuthority authority = new SimpleGrantedAuthority(p);
//            authorities.add(authority);
//        });
//        return authorities;
//    }
//
//    public DecodedJWT decodedJWT(String token) {
//        JWT jwt = new JWT();
//        return jwt.decodeJwt(token);
//    }
//
//    public Map<String, Claim> getClaims(String token) {
//        return decodedJWT(token).getClaims();
//    }
//}