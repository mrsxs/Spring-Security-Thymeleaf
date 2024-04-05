package com.song;

import com.song.mapper.PermissionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PermissionTest {

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    void name() {
        System.out.println(permissionMapper.selectPermissionsByRoleId(2));
    }
}
