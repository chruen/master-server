package com.swjtu.robot.masterserver.utils;

import cn.hutool.core.date.DateTime;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDate;

public class UserIdFactory {
    private StringRedisTemplate stringRedisTemplate;

    public UserIdFactory(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private static final String KEY_PREFIX = "user:id:generate:";

    public long getNextId(){
        LocalDate currentDate = LocalDate.now();
        // 获取当前年份
        int currentYear = currentDate.getYear();
        // 获取当前月份
        int currentMonth = currentDate.getMonthValue();

        // 将年份和月份合并为一个字符串
        String yearMonth = String.format("%d%02d", currentYear, currentMonth);
        Long id = stringRedisTemplate.opsForValue().increment(KEY_PREFIX + yearMonth);
        return Long.parseLong(yearMonth)*1000+id;
    }
}
