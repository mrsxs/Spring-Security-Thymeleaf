package com.song.service.impl;

import com.song.mapper.PermissionMapper;
import com.song.mapper.RoleMapper;
import com.song.mapper.UserMapper;
import com.song.pojo.Permission;
import com.song.pojo.Role;
import com.song.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
 @Autowired
 private RoleMapper roleMapper;
 @Autowired
 private PermissionMapper permissionMapper;

    /**
     * 根据用户名加载用户信息
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 用户名未找到异常
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 通过用户名查询用户信息
        User user = userMapper.selectByUsername(username);
       if (user ==null){
           log.info("用户名{}不存在",username);
           throw new UsernameNotFoundException("用户名或密码错误");
       }
       //查询用户角色信息
        List<Role> roles = roleMapper.selectRolesByUserId(user.getId());
       //查询权限信息
      List<Permission> permissions = permissionMapper.selectPermissionsByRoleId(user.getId());
      //定义一个字符串泛型集合用于存储权限信息
        List<String> authorityList = new ArrayList<>();
        //遍历权限信息
        for(Role role:roles){
            //将角色名称添加到权限集合中 必须以ROLE_开头
            authorityList.add("ROLE_"+role.getName());
        }
        //遍历权限信息
        for(Permission permission:permissions){
            //将权限名称添加到权限集合中
            authorityList.add(permission.getPermit());
        }
        //查看权限信息
        log.info("用户{}的权限信息:{}",username,authorityList);
        // 返回UserDetails接口对象
        org.springframework.security.core.userdetails.User result =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(), user.getPassword(),
                        // 将权限集合转换为权限列表
                         AuthorityUtils.createAuthorityList(authorityList)
                        );
        return result;
    }
}
