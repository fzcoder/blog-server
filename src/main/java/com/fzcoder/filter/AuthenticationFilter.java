package com.fzcoder.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzcoder.bean.JsonResponse;
import com.fzcoder.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class AuthenticationFilter extends BasicAuthenticationFilter {

    private JwtTokenUtils jwtTokenUtils;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtils) {
        super(authenticationManager);
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 请求体的头中是否包含Authorization
        String header = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        // Authorization中是否包含Bearer，有一个不包含时直接返回
        if (header == null || !header.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // 获取Token
        String token = header.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        // 判断Token是否过期
        if (jwtTokenUtils.isExpiration(token)) {
            //登录过期
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            JsonResponse result = new JsonResponse(HttpServletResponse.SC_UNAUTHORIZED, "登录过期, 请重新登录!", null);
            out.write(new ObjectMapper().writeValueAsString(result));
            out.flush();
            out.close();
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
        if (authentication != null) {
            //获取后，将Authentication写入SecurityContextHolder中供后序使用
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            //未登录
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            JsonResponse result = new JsonResponse(HttpServletResponse.SC_FORBIDDEN, "禁止访问！", null);
            out.write(new ObjectMapper().writeValueAsString(result));
            out.flush();
            out.close();
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        // 获取用户名
        String username = jwtTokenUtils.getUsername(token);
        // 获取用户角色
        List<String> roles = jwtTokenUtils.getUserRole(token);
        Collection<GrantedAuthority> authorities = new HashSet<>();
        if (roles!=null) {
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        if (username != null){
            return new UsernamePasswordAuthenticationToken(username, token, authorities);
        }
        return null;
    }
}
