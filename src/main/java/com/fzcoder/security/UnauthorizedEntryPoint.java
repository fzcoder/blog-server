package com.fzcoder.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzcoder.bean.JsonResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未授权统一处理类
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //未登录
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        JsonResponse result = new JsonResponse(HttpServletResponse.SC_UNAUTHORIZED, "无访问权限，请登录！", null);
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
