package com.jajodia.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jajodia.blog.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;


@Component
public class JwtAutenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
//	private AuthenticationManagerBuilder authMgrBuilder;
//	
//    private AuthenticationManager authenticationManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestToken = request.getHeader("Authorization");
		System.out.println(requestToken);
		
		String username=null;
		
		String token=null;
		if(requestToken!=null && requestToken.startsWith("Bearer"))
		{
			token = requestToken.substring(7);
			if(checkIfTokenExistInRedis(token)) {
				ApiResponse apiResponse = new ApiResponse("forced loggedOut JWT token",false);
				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.getWriter().write(convertObjectToJson(apiResponse));
//				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
			}
			try {
				username=jwtTokenHelper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException e)
			{
				System.out.println("unable to get Token");
			}
			catch(ExpiredJwtException e)
			{
				System.out.println("token has expired");
			}
			catch(MalformedJwtException e)
			{
				System.out.println("invalid jwt");
			}
			
		}
		else 
		{
			System.out.println("JWT token does not begin with Bearer");
		}
		
		//once we get the token, now we need to validate it
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if(jwtTokenHelper.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
			else 
			{
				System.out.println("invalid jwt Token");
			}
		}
		else 
		{
			System.out.println("username is null or context is not null");
		}
		
		filterChain.doFilter(request, response);
	}

//	@Override
//	public void afterSingletonsInstantiated() {
//	       	this.authenticationManager = authMgrBuilder.getObject();
//	   }

	private boolean checkIfTokenExistInRedis(String token) {
		return redisTemplate.hasKey(token);
	}

	public String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}


}
