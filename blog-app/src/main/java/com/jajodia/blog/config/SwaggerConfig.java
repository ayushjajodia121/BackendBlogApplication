package com.jajodia.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration

public class SwaggerConfig {
	
	@Bean
	public OpenAPI customizeOpenAPI() {
	    final String securitySchemeName = "bearerAuth";
	    return new OpenAPI()
	      .addSecurityItem(new SecurityRequirement()
	        .addList(securitySchemeName))
	      .components(new Components()
	        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
	          .name(securitySchemeName)
	          .type(SecurityScheme.Type.HTTP)
	          .scheme("bearer")
	          .bearerFormat("JWT")));
	    }
	
//	  @Bean
//	  public OpenAPI springShopOpenAPI() {
//	      return new OpenAPI()
//	              .info(new Info().title("Blogging Backend Application").contact(new Contact().name("Ayush Jajodia").url("ayush.com"))
//	              .description("User,categories,Posts related api for  creating a blog application")
//	              .version("v1.0.1")
//	              .license(new License().name("ayush123456789").url("http://springdoc.org")));
////	              .paths(new Paths().ref);
//      }
//	  @Bean
//	  public GroupedOpenApi api()
//	  {
//	      return GroupedOpenApi.builder()
//	              .group("OpenApiController")
//	              .packagesToScan("com.jajodia")
//	              .build();
//	  }
}