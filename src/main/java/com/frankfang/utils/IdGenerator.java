package com.frankfang.utils;

import java.util.Random;

public class IdGenerator {

    // 基准时间戳，表示时间为：2020-01-01 00:00:00:000
    private static final long BASE_TIMESTAMP = 1577808000000L;

    // 根据时间戳生成Id
    public static long createIdByTimestamp() {
        // 返回id，该方法每秒仅可产生1个id
        return (System.currentTimeMillis() - BASE_TIMESTAMP) / 1000;
    }

    /**
     * id生成器
     * 1. 该算法生成的id由两部分组成：base(基于日期, 共6位) + random(随机产生, 取决于max的值)
     * 2. random的取值范围为：[0, max) 所以共可产生max个随机数
     * 3. 该方法不适用于分布式系统的id生成
     * @param date 规格化的日期
     * @param regex 正则表达式
     * @param max 能产生多少个不同的id
     * @return id 生成的id
     */
    public static Long createIdByDate(String date, String regex, int max) {
        // 将规格化的日期转换为字符串
        String[] array = date.split(regex);
        String base = array[0] + array[1]; // yyyy + MM
        // 将字符串转换为Integer类型
        Long id_base = Long.valueOf(base);
        // 生成随机数
        Random random = new Random();
        // 返回生成的id
        return id_base * max + random.nextInt(max);
    }
}
