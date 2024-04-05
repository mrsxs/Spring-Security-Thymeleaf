package com.song.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
@AllArgsConstructor
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /*登录成功后的跳转地址*/
 private String url;
/*是否是重定向*/
 private boolean isRedirect ;


    /**
     * 登录成功处理 具体业务逻辑
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
     /*   authentication.getPrincipal();*/

        System.out.println("登录成功");
        if (isRedirect){
            /*重定向*/
            response.sendRedirect(url);
        }else {
            //转发
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
}
