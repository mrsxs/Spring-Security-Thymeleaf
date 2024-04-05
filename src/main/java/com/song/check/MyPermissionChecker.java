package com.song.check;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface MyPermissionChecker {


    /**
     * 检查权限
     * @param request 响应
     * @param authentication 认证信息
     * @return true 有权限 false 无权限
     */
    boolean checkPermission(HttpServletRequest request, Authentication authentication);
}
