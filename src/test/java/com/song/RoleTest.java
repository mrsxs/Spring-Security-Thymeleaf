package com.song;

import com.song.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleTest {
    @Autowired
private RoleMapper roleMapper;

    @Test
    void name() {

        System.out.println(roleMapper.selectRolesByUserId(1));
    }
}
