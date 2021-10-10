package com.fzcoder.opensource.blog.config;

import com.fzcoder.opensource.blog.config.autoconfigure.CorsConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private CorsConfigProperties corsConfigProperties;
	
	//解决跨域问题
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedHeaders("*")
			.allowedOrigins(corsConfigProperties.getAllowedOrigins())
			.allowedMethods("*")
			.maxAge(3600);
	}
}