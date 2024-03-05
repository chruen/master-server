package com.swjtu.robot.masterserver.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.symmetric.PBKDF2;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class PasswordEncoder {
    private static int SALT_LEN = 16;
    private static int KEY_LEN = 256;
    private static int HASH_TIMES = 1000;

    public static String encode(String password){
        String salt = RandomUtil.randomString(SALT_LEN);
        return encode(password,salt);
    }

    private static String encode(String password,String salt){
        PBKDF2 pbkdf2 = new PBKDF2("PBKDF2WithHmacSHA1", KEY_LEN, HASH_TIMES);
        char[] p = password.toCharArray();
        byte[] s = salt.getBytes(StandardCharsets.UTF_8);
        return salt+"@"+pbkdf2.encryptHex(p, s);
    }

    public static boolean verifyPassword(String rawPassword,String encodePassword){
        if (encodePassword == null || rawPassword == null) {
            return false;
        }
        if(!encodePassword.contains("@")){
            throw new RuntimeException("密码格式不正确！");
        }
        String[] arr = encodePassword.split("@");
        String salt = arr[0];
        return encodePassword.equals(encode(rawPassword, salt));
    }
}
