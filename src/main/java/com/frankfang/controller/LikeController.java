package com.frankfang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.frankfang.entity.Like;
import com.frankfang.bean.JsonResponse;
import com.frankfang.service.LikeService;
import com.frankfang.utils.HttpUtils;
import com.frankfang.utils.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Api(tags = "点赞功能模块")
@RequestMapping("/api")
@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @ApiOperation(value = "添加点赞记录")
    @PostMapping("/like")
    public Object addLike(@RequestBody Like like, HttpServletRequest request) {
        // 获取文章创建时间
        Date date = new Date();
        // 对时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置时区为东8区(北京时间)
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        like.setVisitorIp(IpUtils.getIpAddr(request));
        like.setDate(sdf.format(date));
        if (likeService.save(like)) {
            return new JsonResponse(HttpUtils.Status_OK, "点赞成功!");
        } else {
            return new JsonResponse(HttpUtils.Status_OK, "点赞失败!");
        }
    }

    @ApiOperation(value = "获取访问者点赞状态")
    @GetMapping("/like/status")
    public Object getLikeStatus(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (params.containsKey("object_name") && params.containsKey("object_id")) {
            QueryWrapper<Like> wrapper = new QueryWrapper<>();
            params.put("visitor_ip", IpUtils.getIpAddr(request));
            wrapper.allEq(true, params, false);
            Map<String, Object> data = new HashMap<>();
            if (likeService.count(wrapper) == 0) {
                data.put("isLike", false);
                data.put("record", null);
            } else {
                String[] sqlSelect = {"id", "date"};
                wrapper.select(sqlSelect);
                data.put("isLike", true);
                data.put("record", likeService.getMap(wrapper));
            }
            return new JsonResponse(data);
        } else {
            return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误!");
        }
    }

    @ApiOperation(value = "取消点赞")
    @DeleteMapping("/like/{id}")
    public Object disLike(@PathVariable("id") Integer id) {
        if (likeService.removeById(id)) {
            return new JsonResponse(HttpUtils.Status_OK, "已取消点赞！");
        } else {
            return new JsonResponse(HttpUtils.Status_BadRequest, "请求异常！");
        }
    }
}
