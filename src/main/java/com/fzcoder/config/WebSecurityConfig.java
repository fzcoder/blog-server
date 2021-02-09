package com.fzcoder.config;

import com.fzcoder.filter.AuthenticationFilter;
import com.fzcoder.filter.LoginFilter;
import com.fzcoder.security.UnauthorizedEntryPoint;
import com.fzcoder.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userService;

	private JwtTokenUtils jwtTokenUtils;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public WebSecurityConfig(@Qualifier("userService") UserDetailsService userService, JwtTokenUtils jwtTokenUtils) {
		this.userService = userService;
		this.jwtTokenUtils = jwtTokenUtils;
	}

	//定义认证规则
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//从数据库中调取用户信息（UserName, Password, Role）
        auth.userDetailsService(userService);
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		// 定制请求的授权规则
		http.authorizeRequests()
			.antMatchers("/api/admin/**").hasRole("admin")
			.antMatchers("/api/auth/**").hasRole("admin")
			.anyRequest().permitAll();
		
		// 允许跨域
		http.cors();
		
		// 由于使用的是JWT，我们这里不需要csrf
		http.csrf().disable();
		
		// 基于token，所以不需要session
        http.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        // 增加登录拦截 
        http.addFilter(new LoginFilter(authenticationManager(), jwtTokenUtils));
        
        // 增加是否登录过滤
        http.addFilter(new AuthenticationFilter(authenticationManager(), jwtTokenUtils));

        // 未授权统一处理
        http.exceptionHandling()
				.authenticationEntryPoint(new UnauthorizedEntryPoint());
	}
}
