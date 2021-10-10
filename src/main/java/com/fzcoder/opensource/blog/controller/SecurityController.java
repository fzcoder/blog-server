package com.fzcoder.opensource.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzcoder.opensource.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fzcoder.opensource.blog.bean.JsonResponse;
import com.fzcoder.opensource.blog.service.MailService;
import com.fzcoder.opensource.blog.service.RedisService;
import com.fzcoder.opensource.blog.service.UserService;
import com.fzcoder.opensource.blog.utils.HttpUtils;
import com.fzcoder.opensource.blog.utils.MailCodeUtils;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SecurityController {

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private TemplateEngine templateEngine;

	@GetMapping("/auth/verify/{type}")
	public Object getVerificationCode(@PathVariable("type") String type, @RequestParam Map<String, Object> params) {
		// 收信人
		String to;
		// redis记录键值
		Object key;
		// 邮件消息内容
		String message;
		// 条件生成器
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		// 设置查询字段
		String[] sqlSelect = {"email"};
		queryWrapper.select(sqlSelect);
		switch (type) {
			case "login":
				if (!params.containsKey("username")) {
					return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误！");
				} else {
					queryWrapper.eq(true, "username", params.get("username"));
					to = userService.getOne(queryWrapper).getEmail();
					key = params.get("username");
					message = "详情：您正在尝试进行登录操作，若非是您本人的行为，请忽略!";
				}
				break;
			case "update":
				if (!params.containsKey("id")) {
					return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误！");
				} else {
					queryWrapper.eq(true, "id", params.get("id"));
					to = userService.getOne(queryWrapper).getEmail();
					key = params.get("id");
					message = "详情：您正在尝试进行修改操作，若非是您本人的行为，请忽略!";
				}
				break;
			case "active":
				if (!params.containsKey("to")) {
					return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误！");
				} else {
					params.put("email", params.get("to"));
					params.remove("to");
					to = params.get("email").toString();
					key = params.get("email");
					message = "详情：您正在尝试进行激活操作，若非是您本人的行为，请忽略!";
				}
				break;
			default:
				return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误！");
		}
		// 随机生成验证码
		String code = MailCodeUtils.getCode();
		// 存入键值对
		redisService.set(key, code);
		// 设置过期时间（5分钟内有效）
		redisService.expire(key, 60 * 5);
		// 设置邮件内容
		Context context = new Context();
		context.setVariable("message", message);
		context.setVariable("code", code);
		String mail = templateEngine.process("mailtemplate.html", context);
		// 发送邮件
		mailService.sendHtmlMail(to, "邮箱验证码", mail);
		return new JsonResponse(HttpUtils.Status_OK, "验证码发送成功！");
	}

	@PostMapping("/auth/verify/check")
	public Object verificationCodeCheck(@RequestParam("key") Object key, @RequestParam("value") Object value) {
		// 验证码校验
		if (redisService.get(key) != null && redisService.get(key).equals(value)) {
			// 删除键值对
			redisService.delete(key);
			// 返回数据
			return new JsonResponse(HttpUtils.Status_OK, "验证码输入正确！");
		} else {
			return new JsonResponse(HttpUtils.Status_BadRequest, "验证码输入错误！");
		}
	}
}
