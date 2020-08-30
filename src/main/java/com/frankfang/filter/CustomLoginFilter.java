package com.frankfang.filter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frankfang.entity.User;
import com.frankfang.bean.JsonResponse;
import com.frankfang.utils.HttpUtils;
import com.frankfang.utils.JwtTokenUtils;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public CustomLoginFilter(AuthenticationManager authenticationManager) {

		this.authenticationManager = authenticationManager;
		super.setFilterProcessesUrl("/api/login");
	}

	/**
	 * 接收并解析用户凭证，出現错误时，返回JSON数据前端
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 未登录出现账号或密码错误，使用JSON进行提示
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				JsonResponse result = new JsonResponse(HttpUtils.Status_UnAuthorized, "账号或密码错误！");
				out.write(new ObjectMapper().writeValueAsString(result));
				out.flush();
				out.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException();
		}
		
	}

	/**
	 * 用户登录成功后，生成token,并且返回JSON数据给前端
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) {

		try {
			// 获取用户
			User user = (User) auth.getPrincipal();
			// 获取角色信息
			Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
			List<String> roles = new ArrayList<>();
			for (GrantedAuthority authority : authorities) {
				roles.add(authority.getAuthority());
			}

			// JSON Web Token构建
			String token = JwtTokenUtils.createToken(user.getUsername(), roles);

			try {
				// 登录成功时，返回JSON格式进行提示
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				Map<String, Object> data = new HashMap<>();
				data.put("id", user.getId());
				data.put("username", user.getUsername());
				data.put("token", "Bearer " + token);
				JsonResponse result = new JsonResponse(HttpUtils.Status_OK, "登录成功！", data);
				out.write(new ObjectMapper().writeValueAsString(result));
				out.flush();
				out.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
