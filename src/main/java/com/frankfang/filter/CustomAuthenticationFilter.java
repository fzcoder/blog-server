package com.frankfang.filter;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frankfang.utils.JwtTokenUtils;

public class CustomAuthenticationFilter extends BasicAuthenticationFilter {
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
		try {
			//请求体的头中是否包含Authorization
			String header = request.getHeader("Authorization");
			//Authorization中是否包含Bearer，有一个不包含时直接返回
			if (header == null || !header.startsWith("Bearer ")) {
				chain.doFilter(request, response);
				// responseJson(response);
				return;
			}
			//获取权限失败，会抛出异常
			UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
			//获取后，将Authentication写入SecurityContextHolder中供后序使用
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		} catch (Exception e) {
			responseJson(response);
			e.printStackTrace();
		}
	}
	
	private void responseJson(HttpServletResponse response) {
		try {
			//未登录时使用JSON进行提示
			response.setContentType("application/json;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			PrintWriter out = response.getWriter();
			Map<String,Object> map = new HashMap<>();
			map.put("status",HttpServletResponse.SC_FORBIDDEN);
			map.put("message", "请登录");
			out.write(new ObjectMapper().writeValueAsString(map));
			out.flush();
			out.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String header) {
		String token = header.replace(JwtTokenUtils.TOKEN_PREFIX, "");
		String username = JwtTokenUtils.getUsername(token);
		List<String> roles = JwtTokenUtils.getUserRole(token);
		Collection<GrantedAuthority> authorities = new HashSet<>();
        if (roles!=null) {
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        if (username != null){
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
        return null;
	}
}
