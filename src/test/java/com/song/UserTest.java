package com.song;

import com.song.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class UserTest {


    @Autowired
    private UserMapper userMapper;

    @Test
    void name() {
        System.out.println(userMapper.selectByUsername("admin"));
    }

    @Test
    void password() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("user");
        System.out.println(encode);

    }
}
