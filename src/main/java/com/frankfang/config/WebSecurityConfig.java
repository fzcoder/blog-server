package com.frankfang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.frankfang.filter.CustomAuthenticationFilter;
import com.frankfang.filter.CustomLoginFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService service;
	
	@Bean 
	PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder();
	}
	
	/* @Bean
	@Override
	public AuthenticationManager authenticationManagerBean () throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	@Override
	protected UserDetailsService userDetailsService () {
		return super.userDetailsService ();
	} */
	
	//定义认证规则
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//从数据库中调取用户信息（UserName, Password, Role）
        auth.userDetailsService(service);
		// auth.inMemoryAuthentication().withUser("root").password("$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq").roles("admin");
    }
	
	// 自定义登录成功返回信息
	/* private class CustomSuccessHandler implements AuthenticationSuccessHandler {
				
		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
			 
			// JSON Web Token构建
			 String token = Jwts.builder()    
					 		//此处为自定义的、实现org.springframework.security.core.userdetails.UserDetails的类，需要和配置中设置的保持一致
			            	//此处的subject可以用一个用户名，也可以是多个信息的组合，根据需要来定
			            	.setSubject(((User) authentication.getPrincipal()).getUsername())    
			            	//设置token过期时间，24小時
			            	.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000)) 
			            
			            	//设置token签名、密钥
			            	.signWith(SignatureAlgorithm.HS512, "MyJwtSecret")       
			            
			            	.compact(); 
			//返回token
			 // response.addHeader("Authorization", "Bearer " + token);
			 response.setContentType("application/json;charset=utf-8");
			 PrintWriter out = response.getWriter();
			 JsonResponse result = new JsonResponse();
			 Map<String, Object> data = new HashMap<>();
			 data.put("token", "Bearer " + token);
			
			 result.setStatus(200);
			 result.setMessage("登录成功！");
			 result.setData(data);
			
			 out.write(new ObjectMapper().writeValueAsString(result));
			 out.flush();
			 out.close();			
		}
		
	} */
	
	//自定义登录失败信息
	/* private class CustomFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
			JsonResponse result = new JsonResponse();
			result.setStatus(401);
			if(exception instanceof LockedException) {
				result.setMessage("账户已被锁定！");
			} else if (exception instanceof DisabledException) {
				result.setMessage("账户已被禁用！");
			} else if (exception instanceof BadCredentialsException) {
				result.setMessage("账户名或密码输入错误！");
			} else if (exception instanceof AccountExpiredException) {
				result.setMessage("账户已过期，登录失败！");
			} else if (exception instanceof CredentialsExpiredException) {
				result.setMessage("密码已过期，登录失败！");
			} else {
				result.setMessage("登录失败！");
			}
			result.setData(null);
			out.write(new ObjectMapper().writeValueAsString(result));
			out.flush();
			out.close();
		}
		
	} */
	
	// 自定义退出处理
	/* private class CustomLogoutHandler implements LogoutHandler {
		
		@Override
		public void logout(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) {
			
		}
		
	} */
	
	// 自定义退出成功处理
	/* private class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

		@Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
			response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
			JsonResponse result = new JsonResponse();
			result.setStatus(200);
			result.setMessage("退出成功！");
			result.setData(null);
			out.write(new ObjectMapper().writeValueAsString(result));
			out.flush();
			out.close();
		}
		
	} */
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		// 定制请求的授权规则
		http.authorizeRequests()
			.antMatchers("/api/admin/**").hasRole("admin")
			.anyRequest().permitAll();
			
		
		// 开启登录功能
		/* http.formLogin()
			.loginPage("/loginPage")
			.loginProcessingUrl("/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.successHandler(new CustomSuccessHandler())
			.failureHandler(new CustomFailureHandler())
			.permitAll(); */
		
		// 开启注销功能
		/* http.logout()
			.logoutUrl("/logout")
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.addLogoutHandler(new CustomLogoutHandler())
			.logoutSuccessHandler(new CustomLogoutSuccessHandler()); */
		
		// 允许跨域
		http.cors();
		
		// 由于使用的是JWT，我们这里不需要csrf
		http.csrf().disable();
		
		// 基于token，所以不需要session
        http.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and()
        	.authorizeRequests();
        
        // 增加登录拦截 
        http.addFilter(new CustomLoginFilter(authenticationManager()));
        
        // 增加是否登录过滤
        http.addFilter(new CustomAuthenticationFilter(authenticationManager()));
	}
}
