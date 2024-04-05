package com.song.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 修改用户信息 需要用户权限
     * @PostAuthorize 注解用于判断用户是否具有某个权限，如果有就可以访问 方法执行后再判断权限 很少用
     *                hasAuthority() 方法用于判断； 用户是否具有某个权限
     * @return
     */
    @RequestMapping("modify")
    @PostAuthorize("hasAuthority('user:modify')")
     public String modify() {
        System.out.println("modify");
          return "user modify";
     }
    //hasAuthority() 方法用于判断；
    // 用户是否具有某个权限 hasAnyAuthority() 方法用于判断用户是否具有多个权限 只要有一个权限就可以
    // hasRole() 方法用于判断用户是否具有某个角色 hasAnyRole() 方法用于判断用户是否具有多个角色 只要有一个角色就可以
    // @PreAuthorize 注解用于判断用户是否具有某个权限，如果有就可以访问
              //hasAuthority() 方法用于判断； 用户是否具有某个权限
    // hasAnyAuthority() 方法用于判断用户是否具有多个权限 只要有一个权限就可以
    // hasRole() 方法用于判断用户是否具有某个角色 hasAnyRole() 方法用于判断用户是否具有多个角色 只要有一个角色就可以

    // @Secured 注解用于判断用户是否具有某个角色，如果有就可以访问
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('user:add')")

    public String add() {

        return "user add";
    }

    /**
     * 查询用户列表 需要管理员权限
     *
     * @return {@link String}
     * @secured 注解 用于权限控制
     */
    @RequestMapping("/list")
    @Secured("ROLE_管理员")
    public String list() {
        return "user list";
    }
}
