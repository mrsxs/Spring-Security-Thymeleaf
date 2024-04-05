package com.song.check.impl;

import com.song.check.MyPermissionChecker;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyPermissionCheckerImpl implements MyPermissionChecker {

    /**
     * 每次请求 都传递一个参数 参数名perm 参数值是权限标记 比如user:add
     * 检查权限 有权限返回true 无权限返回false
     *
     * @param request        请求
     * @param authentication 认证信息
     * @return true 有权限 false 无权限
     */
    @Override
    public boolean checkPermission(HttpServletRequest request, Authentication authentication) {
/* .requestMatchers("/login", "/userLogin").permitAll()
                //设置静态资源不需要认证
                .requestMatchers("/js/**", "/css/**", "/images/**")*/
        //获取请求路径
        String path = request.getServletPath();
        //判断请求地址是否直接放行
        if ("/login".equals(path) || "/userLogin".equals(path) || path.startsWith("/js/") || path.startsWith("/css/") || path.startsWith("/images/")) {
            return true;
        }
        //获取请求参数
        String perm = request.getParameter("perm");
          perm = (perm !=null && !perm.trim().isEmpty())?perm:"none";
        //判断是否已经登录
        if (authentication != null && authentication.isAuthenticated()) {
            //获取当前用户的权限信息
            Collection<? extends GrantedAuthority> authorities =
                    authentication.getAuthorities();
            //遍历权限信息
            if (authorities.contains(new SimpleGrantedAuthority(perm))) {
                //有权限
                return true;
            }

        }else{
            //未登录
            return false;
        }
        System.out.println("没有权限");
        //没有权限
        return false;

    }
}
