package com.song.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.song.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author song
 * @since 2024-04-04
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {


    /**
     * 根据角色主键查询权限集合
     */
    List<Permission> selectPermissionsByRoleId(Integer userId);
}
