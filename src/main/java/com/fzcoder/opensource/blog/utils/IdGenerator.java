package com.fzcoder.opensource.blog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IdGenerator {
    /**
     * 根据日期生成id
     * id格式: yyyy(年) + MM(月) + hash_code(文章的哈希值，由当前时间相对本月开始时间的偏移量决定)
     * 哈希函数: h(time_offset, max) = time_offset % (max - 1)
     * 算法思想: 相当于变相的实现了一个散列表(hash)
     * @param date 当前时间
     * @param max 所能生成的id的最大数量(取10,100,1000,10000...)
     * @return 生成的id
     * @throws ParseException 解析异常
     */
    public static Long createIdByDate(Date date, int max) throws ParseException {
        // 将日期转换为字符串数组
        String[] date_arr = DateUtil.toDateArray(
                date, DateUtil.TIMEZONE_UTC8E, DateUtil.FORMAT_PATTERN_1, "-"
        );
        // 获取当前年份和月份
        String year = date_arr[0];
        String month = date_arr[1];
        // 获取当前时间与当月开始日期的偏移值
        long time_offset = System.currentTimeMillis() - DateUtil
                .parse(DateUtil.TIMEZONE_UTC8E,
                        DateUtil.FORMAT_PATTERN_2,
                        year + "-" + month + "-01")
                .getTime();
        // 生成id
        long hash_code = time_offset % (max - 1); // 哈希值
        return Long.parseLong(year + month) * max + hash_code;
    }

    /**
     * 生成62进制随机数
     * @param figure 随机数的位数
     * @return 返回字符串类型的随机数
     */
    public static String createIdBy62BaseRandom (int figure) {
        String base = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < figure; i++) {
            int index = (int) (Math.random() * base.length());
            code.append(base.charAt(index));
        }
        return code.toString();
    }
}
