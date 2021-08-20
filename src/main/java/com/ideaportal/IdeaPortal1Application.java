package com.ideaportal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ideaportal.jwt.AuthFilter;


@SpringBootApplication
@ComponentScan(basePackages={"com.ideaportal"})
public class IdeaPortal1Application extends SpringBootServletInitializer {
	 @Value("${ideaportal.jwt.secret-key}")
	    public String jwtSecretKey;

	public static void main(String[] args) {
		SpringApplication.run(IdeaPortal1Application.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	 @Bean
	    public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
	        final FilterRegistrationBean<AuthFilter> registrationBean = (FilterRegistrationBean<AuthFilter>)new FilterRegistrationBean<AuthFilter>();
	        final AuthFilter authFilter = new AuthFilter(this.jwtSecretKey);
	        registrationBean.setFilter(authFilter);
	        registrationBean.addUrlPatterns(new String[] { "/api/user/*" });
	        return registrationBean;
	    }
	 @Bean
	    public WebMvcConfigurer corsConfigurer() 
	    {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**").allowedOrigins("http://localhost:8081");
	            }
	        };
	    }
}
