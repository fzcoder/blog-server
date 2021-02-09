package com.fzcoder.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzcoder.bean.JsonResponse;
import com.fzcoder.entity.User;
import com.fzcoder.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JwtTokenUtils jwtTokenUtils;

    public LoginFilter(AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
        super.setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = null;
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                            new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 获取用户
        User user = (User) authResult.getPrincipal();
        // 获取角色信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        boolean isRemember = false;
        // 获取请求参数
        String isRememberMe = request.getParameter("isRememberMe");
        if ("true".equals(isRememberMe)) {
            isRemember = true;
        }
        // JSON Web Token构建
        String token = jwtTokenUtils.createToken(user.getUsername(), roles, isRemember);
        // 登录成功时，返回JSON格式进行提示
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("token", JwtTokenUtils.TOKEN_PREFIX + token);
        JsonResponse result = new JsonResponse(HttpServletResponse.SC_OK, "登录成功！", data);
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 登录失败
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        JsonResponse result = new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "账号或密码错误！", null);
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
