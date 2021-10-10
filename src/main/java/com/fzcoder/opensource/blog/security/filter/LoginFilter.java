package com.fzcoder.opensource.blog.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzcoder.opensource.blog.entity.User;
import com.fzcoder.opensource.blog.utils.JwtTokenUtil;
import com.fzcoder.opensource.blog.utils.response.R;
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

    private JwtTokenUtil jwtTokenUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
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
        String token = jwtTokenUtil.createToken(user.getUsername(), roles, isRemember);
        // 登录成功时，返回JSON格式进行提示
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("token", JwtTokenUtil.TOKEN_PREFIX + token);
        out.write(new ObjectMapper().writeValueAsString(R.ok("登录成功！", data)));
        out.flush();
        out.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 登录失败
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(R.badRequest("账号或密码错误！", null)));
        out.flush();
        out.close();
    }
}
