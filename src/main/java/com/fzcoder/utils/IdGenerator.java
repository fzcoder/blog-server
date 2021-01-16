package com.fzcoder.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IdGenerator {

    // 基准时间戳，表示时间为：2020-01-01 00:00:00:000
    private static final long BASE_TIMESTAMP = 1577808000000L;

    private static String[] getDateArray(Date date, String timeZone) {
        // 设置时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 设置时区
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        // 将日期转换为字符串数组
        return sdf.format(date).split("-");
    }

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
        String[] date_arr = getDateArray(date, "Asia/Shanghai");
        // 获取当前年份和月份
        String year = date_arr[0];
        String month = date_arr[1];
        // 设置时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 设置时区
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 获取当前时间与当月开始日期的偏移值
        long time_offset = System.currentTimeMillis() - sdf
                .parse(year + "-" + month + "-01")
                .getTime();
        // 生成id
        long hash_code = time_offset % (max - 1); // 哈希值
        return Long.valueOf(year + month) * max + hash_code;
    }

    public static Long getMaxIdValueForCreateByDate(Date date, int max) {
        String[] date_arr = getDateArray(date, "Asia/Shanghai");
        // 获取当前年份和月份
        String year = date_arr[0];
        String month = date_arr[1];
        return Long.valueOf(year + month) * max + max - 1;
    }

    /**
     * 生成62进制随机数
     * @param figure 随机数的位数
     * @return 返回字符串类型的随机数
     */
    public static String createIdBy62BaseRandom (int figure) {
        String base = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String code = "";
        for (int i = 0; i < figure; i++) {
            int index = (int) (Math.random() * base.length());
            code += base.charAt(index);
        }
        return code;
    }
}
