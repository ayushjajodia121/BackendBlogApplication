package com.jajodia.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jajodia.blog.security.JWTAuthenticationEntryPoint;
import com.jajodia.blog.security.JwtAutenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	
	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAutenticationFilter jwtAutenticationFilter;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
//	@Autowired
//	void registerProvider(AuthenticationManagerBuilder builder) {
//	   builder.authenticationProvider(new CognitoJwtAuthProvider());
//	}
	
	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration authConfig) throws Exception {
	    System.out.println("recahed Authentication Manager bean");

	    return authConfig.getAuthenticationManager();
	}

	
	
//	@Bean
//    public UserDetailsService userDetailsService() {
//        return new CustomUserDetailService();
//    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.csrf()
        	.disable()
            .authorizeHttpRequests()
            .antMatchers("/api/v1/auth/login").permitAll()
            .antMatchers("/api/v1/users/createUser").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http
        	.addFilterBefore(jwtAutenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); 
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	 
	    return authProvider;
	}
   
//	@Bean
//    public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
//        EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean =
//            EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();
//        contextSourceFactoryBean.setPort(0);
//        return contextSourceFactoryBean;
//    }
//
//    @Bean
//    AuthenticationManager ldapAuthenticationManager( BaseLdapPathContextSource contextSource) {
//        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
//        factory.setUserDetailsContextMapper((UserDetailsContextMapper) new CustomUserDetailService());
//        return factory.createAuthenticationManager();
//    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();	
    }
    
//    @Bean
//    JwtAuthenticationFilter jwtAuthFilter(AuthenticationManagerBuilder builder) {
//       return new JwtAuthenticationFilter(builder);
//    }
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception{
//    	return super.authenticationManagerBean();
//    }

}
