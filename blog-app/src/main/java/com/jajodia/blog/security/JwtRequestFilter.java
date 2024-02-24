//package com.psi.ipg.account.filter;
//
//import com.auth0.jwt.interfaces.Claim;
//import com.psi.ipg.account.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//@Component
//@Order(2)
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String authorizationHeader = request.getHeader("Authorization");
//        String username = null;
//        String token = null;
//        Map<String, Claim> otherDetails = new HashMap<>();
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            token = authorizationHeader.substring(7);
//            try {
//                if(jwtUtil.extractExpiration(token).before(new Date())){
//                    logger.error("Token Expired!");
//                    throw new ServletException("token_expired");
//                }
//                username = jwtUtil.extractUsername(token);
//                otherDetails = jwtUtil.getClaims(token);
//
//            } catch (IllegalArgumentException e) {
//                logger.error("an error occurred during getting username from token", e);
//                throw new ServletException("Unknown error");
//            }
//        }
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            Set<GrantedAuthority> authorities = jwtUtil.getAuthorities(token);
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
//            usernamePasswordAuthenticationToken.setDetails(otherDetails);
//            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}