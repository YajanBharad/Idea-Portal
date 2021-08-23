package com.ideaportal;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;




import com.ideaportal.jwt.AuthFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
@ComponentScan(basePackages={"com.ideaportal"})
@EnableSwagger2
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
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		registrationBean.setFilter(new CorsFilter(source));
		registrationBean.setOrder(0);
		return registrationBean;
	}
	 @Bean
	    public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
	        final FilterRegistrationBean<AuthFilter> registrationBean = (FilterRegistrationBean<AuthFilter>)new FilterRegistrationBean<AuthFilter>();
	        final AuthFilter authFilter = new AuthFilter(this.jwtSecretKey);
	        registrationBean.setFilter(authFilter);
	        registrationBean.addUrlPatterns(new String[] { "/api/user/*" });
	        return registrationBean;
	    }
	 
}
