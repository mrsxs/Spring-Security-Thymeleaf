package com.song.encoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class MyPassWordEncoder implements PasswordEncoder {
    /**
     * 是否需要升级密码解析
     *
     * @param encodedPassword 编码密码
     * @return boolean
     */
    @Override
    public boolean upgradeEncoding(String encodedPassword) {

        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }

    /**
     * 加密
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        log.info("自定义密码加密器");
        return rawPassword.toString();
    }

    /**
     * 匹配
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.info("自定义密码解析器");
        return encode(rawPassword).equals(encodedPassword);
    }
}
