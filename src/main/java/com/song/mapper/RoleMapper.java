package com.song.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.song.pojo.Role;
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
public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 根据用户主键查询角色集合
     */
   List<Role> selectRolesByUserId(Integer userId);
}
