package com.frankfang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frankfang.entity.Like;
import com.frankfang.entity.record.ArticleRecord;
import com.frankfang.entity.record.WebsiteRecord;
import com.frankfang.bean.PageRequest;
import com.frankfang.bean.JsonResponse;
import com.frankfang.service.*;
import com.frankfang.utils.HttpUtils;
import com.frankfang.utils.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Api(tags = "统计模块接口")
@RequestMapping("/api")
@RestController
public class CountController {

    @Autowired
    private CountService countService;

    @Autowired
    private WebsiteRecordService websiteRecordService;

    @Autowired
    private ArticleRecordService articleRecordService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LikeService likeService;

    // 一天的开始时间
    private static final String TIME_START = "00:00:00";

    // 一天的结束时间
    private static final String TIME_END = "23:59:59";

    @ApiOperation(value = "统计文章阅读量")
    @PostMapping("/count/article")
    public Object hasNewReader(@RequestParam("id") Integer id, HttpServletRequest request) {
        // 获取文章创建时间
        Date date = new Date();
        // 对时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置时区为东8区(北京时间)
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        ArticleRecord entity = new ArticleRecord();
        entity.setArticleId(id);
        entity.setDate(sdf.format(date));
        // 获取访问者的ip地址
        entity.setVisitorIp(IpUtils.getIpAddr(request));
        if (articleRecordService.save(entity)) {
            return new JsonResponse(HttpUtils.Status_OK, "");
        } else {
            return new JsonResponse(HttpUtils.Status_BadRequest, "");
        }
    }

    @ApiOperation(value = "统计网站访问量")
    @PostMapping("/count/website")
    public Object hasNewVisitor(HttpServletRequest request) {
        // 获取文章创建时间
        Date date = new Date();
        // 对时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置时区为东8区(北京时间)
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 创建实体
        WebsiteRecord entity = new WebsiteRecord();
        // 设置访问时间
        entity.setDate(sdf.format(date));
        // 获取ip地址
        entity.setVisitorIp(IpUtils.getIpAddr(request));
        // 获取地理位置
        // entity.setVisitorAddress(AddressUtils.getCity(entity.getVisitorIp()));
        // entity.setVisitorAddress(AddressUtils.getCity("117.136.42.86"));
        // 添加记录
        if (websiteRecordService.save(entity)) {
            return new JsonResponse(HttpUtils.Status_OK, "");
        } else {
            return new JsonResponse(HttpUtils.Status_BadRequest, "");
        }
    }

    @ApiOperation(value = "获取统计信息")
    @GetMapping("/count/{type}")
    public Object getCount(@PathVariable("type") String type, @RequestParam Map<String, Object> params) {
        switch (type) {
            case "website":
                if (params.isEmpty()) {
                    // 获取网站访问量
                    Map<String, Object> data = new HashMap<>();
                    data.put("hits", websiteRecordService.count());
                    return new JsonResponse(data);
                } else {
                    return new JsonResponse(null);
                }
            case "article":
                if (params.isEmpty()) {
                    // 获取文章总数
                    Map<String, Object> data = new HashMap<>();
                    data.put("total", articleService.count());
                    return new JsonResponse(data);
                } else if (params.containsKey("article_id") && params.get("details").toString().equals("view")) {
                    // 获取文章阅读量
                    QueryWrapper<ArticleRecord> wrapper = new QueryWrapper<>();
                    wrapper.eq(true, "article_id", params.get("article_id"));
                    return new JsonResponse(articleRecordService.count(wrapper));
                } else {
                    return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误!");
                }
            case "like":
                if (params.containsKey("object_name") && params.containsKey("object_id")) {
                    // 获取点赞数量
                    QueryWrapper<Like> wrapper = new QueryWrapper<>();
                    wrapper.allEq(true, params, false);
                    Map<String, Object> data = new HashMap<>();
                    data.put("total", likeService.count(wrapper));
                    return new JsonResponse(data);
                } else {
                    return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误！");
                }
            default:
                return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误!");
        }
    }

    @ApiOperation(value = "获取记录列表")
    @PostMapping("/admin/records/{type}")
    public Object getRecordPage(@PathVariable("type") String type, @RequestParam Map<String, Object> params, @RequestBody PageRequest pageRequest) {
        if (type.equals("website")) {
            QueryWrapper<WebsiteRecord> queryWrapper = new QueryWrapper<>();
            String[] sqlSelect = {"id", "date", "visitor_ip", "visitor_address"};
            queryWrapper.allEq(true, params, false);
            queryWrapper.orderBy(true, !pageRequest.isReverse(), pageRequest.getOrderBy());
            queryWrapper.select(sqlSelect);
            IPage<Map<String, Object>> page = websiteRecordService.pageMaps(new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize()), queryWrapper);
            return new JsonResponse(page);
        } else {
            return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误！");
        }
    }

    @GetMapping("/admin/chart/website")
    public Object getChart(@RequestParam("start") String start, @RequestParam("end") String end, @RequestParam Map<String, Object> params) {
        // 对日历对象进行实例化，可设置时区和国际化(如设置: Locale.CHINA)
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        // 对时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 将字符串转换为Date类型
            // 起始时间
            Date startDate = sdf.parse(start);
            // 结束时间
            Date endDate = sdf.parse(end);
            // 将日历起始日期设置为起始日期
            calendar.setTime(startDate);
            // 数据容器
            List<Map<String, Object>> list = new LinkedList<>();
            while (calendar.getTime().compareTo(endDate) <= 0) {
                String oneDay = sdf.format(calendar.getTime());
                // 一天的起始日期
                String oneDayBegin = oneDay + " " + TIME_START;
                // 一天的结束日期
                String oneDayEnd = oneDay + " " + TIME_END;
                // 生成条件构造器
                QueryWrapper<WebsiteRecord> queryWrapper = new QueryWrapper<>();
                queryWrapper.between(true, "date", oneDayBegin, oneDayEnd);
                Map<String, Object> elem = new HashMap<>();
                elem.put("date", oneDay);
                elem.put("hits", websiteRecordService.count(queryWrapper));
                list.add(elem);
                // 将日历向后推进一天
                calendar.add(Calendar.DATE, 1);
            }
            return new JsonResponse(list);
        } catch (ParseException e) {
            log.error("字符串转换日期发生异常!", e);
            return new JsonResponse(HttpUtils.Status_InternalServerError, "服务端异常！");
        }
    }

    @GetMapping("/dynamic")
    public Object getDynamic(@RequestParam("uid") Serializable userId, @RequestParam("type") String type, @RequestParam("part_num") long partNum) {
        return new JsonResponse(countService.getDynamicList(userId, type, partNum));
    }
}
