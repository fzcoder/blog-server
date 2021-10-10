package com.fzcoder.opensource.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzcoder.opensource.blog.utils.response.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未授权统一处理类
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        //未登录
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(R.unauthorized("无访问权限，请登录！", null)));
        out.flush();
        out.close();
    }
}
