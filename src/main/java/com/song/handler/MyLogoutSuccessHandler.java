package com.song.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * 退出成功后的处理
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //退出成功后的处理
        //销毁session
        request.getSession().invalidate();
 /*       //清除上下文
        SecurityContextHolder.clearContext();*/
        //设置未认证
        authentication.setAuthenticated(false);
        System.out.println("退出成功");
        //重定向到登录页面
        response.sendRedirect("/login");

    }
}
