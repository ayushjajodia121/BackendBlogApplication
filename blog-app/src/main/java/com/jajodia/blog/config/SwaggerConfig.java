//package com.jajodia.blog.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//	public static final  String AUTHORIZATION_HEADER = "Authorization";
//
//	private ApiKey apiKeys(){
//		return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
//	}
//	private List<SecurityContext> securityContexts(){
//		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
//	}
//	private List<SecurityReference> sf(){
//		AuthorizationScope scope = new AuthorizationScope("global","accessEverything");
//		return Arrays.asList(new SecurityReference("scope",new AuthorizationScope[]{scope}));
//	}
//	 @Value("${enable.swagger}")
//	    private boolean enableSwaggerPlugin;
//
//	    @Bean
//	    public Docket api() {
//	        return new Docket(DocumentationType.SWAGGER_2)
//	                .select()
//	                .apis(RequestHandlerSelectors.basePackage("com.psi.ipg.account.controller"))
//	                .paths(PathSelectors.any())
//	                .build()
//	                .securityContexts(securityContexts())
//					.securitySchemes(Arrays.asList(apiKeys()))
//	                .enable(enableSwaggerPlugin)
//	                .apiInfo(apiInfo());
//	    }
//
//	    private ApiInfo apiInfo() {
//	        return new ApiInfoBuilder()
//	                .title("Blog-App API")
//	                .description("Blog-App Api Documentation")
//	                .termsOfServiceUrl("http://xyz.com/termsofuse")
//	                .contact(new Contact("Blog-App", "http://xyz.com", "contact@xyz.com"))
//	                .license("Copyright XYZ")
//	                .licenseUrl("http://xyz.com/license")
//	                .version("1.0")
//	                .build();
//	    }
//
//}