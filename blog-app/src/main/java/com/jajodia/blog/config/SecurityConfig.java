package com.jajodia.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jajodia.blog.security.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.csrf()
        	.disable()
            .authorizeHttpRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
        return http.build();
    }
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	     
	    authProvider.setUserDetailsService(userDetailsService());
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

}
