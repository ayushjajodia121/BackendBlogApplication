package com.jajodia.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jajodia.blog.exception.UserTokenLoggedOutExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
			try {
				if(request.getAttribute("redirected") == null) {
					username = jwtTokenHelper.getUsernameFromToken(token);
					if (checkIfTokenExistInRedis(token)) {
//						throw new ServletException("");
//						request.setAttribute("redirected", true);
//						response.sendRedirect(request.getContextPath() + "/api/v1/users/invalidUser");

//						return;
						//throw new UserTokenLoggedOutExpiredException("forced loggedOut token used");

						//response.getWriter().write("Throwing custom exception.");
					}
					filterChain.doFilter(request, response);
//					return;
				}
//				return;
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
			catch (UserTokenLoggedOutExpiredException ex) {
				response.sendRedirect(request.getContextPath() + "/api/v1/users/invalidUser");
				//return;
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

	private boolean checkIfTokenExistInRedis(String token) {
		return redisTemplate.hasKey(token);
	}

//	@Override
//	public void afterSingletonsInstantiated() {
//	       	this.authenticationManager = authMgrBuilder.getObject();
//	   }


}
