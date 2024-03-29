package com.fzcoder.opensource.blog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fzcoder.opensource.blog.entity.User;
import com.fzcoder.opensource.blog.bean.JsonResponse;
import com.fzcoder.opensource.blog.service.RedisService;
import com.fzcoder.opensource.blog.service.UserService;
import com.fzcoder.opensource.blog.utils.HttpUtils;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private RedisService redisService;

	@GetMapping("/user/{id}")
	public Object getUserInfo(@PathVariable("id") Integer id, @RequestParam Map<String, Object> params) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		String[] sqlSelect = { "nickname", "avatar", "motto", "introduction", "home_page", "github", "gitee" };
		wrapper.eq(true, "id", id);
		wrapper.select(sqlSelect);
		return new JsonResponse(service.getMap(wrapper));
	}

	@GetMapping("/admin/{id}")
	public Object viewUserById(@PathVariable("id") Integer id, @RequestParam Map<String, Object> params) {
		return new JsonResponse(service.getById(id));
	}

	@GetMapping("/admin/{id}/{column}")
	public Object getColumnById(@PathVariable("id") Integer id, @PathVariable("column") String column) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.select(column);
		wrapper.eq("id", id);
		return new JsonResponse(service.getMap(wrapper));
	}

	@PutMapping("/admin")
	public Object updateUser(@RequestBody User user) {
		user.setEnabled(true);
		user.setLocked(false);
		if (service.updateById(user)) {
			return new JsonResponse(HttpUtils.Status_OK, "修改成功！");
		} else {
			return new JsonResponse(HttpUtils.Status_BadRequest, "修改失败！");
		}
	}

	@PutMapping("/admin/{id}/{column}")
	public Object updateUser(@PathVariable("id") Integer id, @RequestBody Map<String, Object> params, @PathVariable("column") String column) {
		switch (column) {
			case "avatar":
				if (!params.containsKey("avatar") || params.get("avatar").equals("")) {
					return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误！");
				} else {
					UpdateWrapper<User> wrapper = new UpdateWrapper<>();
					String sqlSet = "avatar=" + "'" + params.get("avatar").toString() + "'";
					wrapper.setSql(true, sqlSet);
					if (service.update(wrapper)) {
						return new JsonResponse(HttpUtils.Status_OK, "头像修改成功！");
					} else {
						return new JsonResponse(HttpUtils.Status_BadRequest, "头像修改失败！");
					}
				}
			case "email":
				if (!params.containsKey("email") || !params.containsKey("code")) {
					return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误！");
				} else {
					String code = redisService.get(params.get("email").toString()).toString();
					if (code.equals(params.get("code").toString())) {
						UpdateWrapper<User> wrapper = new UpdateWrapper<>();
						String sqlSet = "email=" + "'" + params.get("email").toString() + "'";
						wrapper.setSql(true, sqlSet);
						if (service.update(wrapper)) {
							return new JsonResponse(HttpUtils.Status_OK, "邮箱修改成功！");
						} else {
							return new JsonResponse(HttpUtils.Status_InternalServerError, "服务端异常！");
						}
					} else {
						return new JsonResponse(HttpUtils.Status_BadRequest, "验证码错误！");
					}
				}
			case "password":
				if (!params.containsKey("old_pwd") || !params.containsKey("new_pwd") || !params.containsKey("code")) {
					return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误！");
				} else {
					// 从数据库中获取key值
					String code = redisService.get(id.toString()).toString();
					// 验证码比对
					if (code.equals(params.get("code").toString())) {
						// 验证通过，删除键值对
						redisService.delete(id.toString());
						BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
						QueryWrapper<User> queryWrapper = new QueryWrapper<>();
						String[] sqlSelect = {"password"};
						queryWrapper.eq(true, "id", id);
						queryWrapper.select(sqlSelect);
						if (encoder.matches(params.get("old_pwd").toString(), service.getOne(queryWrapper).getPassword())) {
							// 对密码进行加密 ( 这里使用BCrypt强哈希函数进行2^10次迭代 )
							String password = encoder.encode(params.get("new_pwd").toString());
							// 生成条件构造器
							UpdateWrapper<User> wrapper = new UpdateWrapper<>();
							String sqlSet = "password=" + "'" + password + "'";
							wrapper.setSql(true, sqlSet);
							if (service.update(wrapper)) {
								// 向前端返回结果
								return new JsonResponse(HttpUtils.Status_OK, "密码修改成功！");
							} else {
								return new JsonResponse(HttpUtils.Status_InternalServerError, "服务端异常！");
							}
						} else {
							return new JsonResponse(HttpUtils.Status_BadRequest, "密码错误！");
						}
					} else {
						return new JsonResponse(HttpUtils.Status_BadRequest, "验证码错误！");
					}
				}
			default:
				return new JsonResponse(HttpUtils.Status_BadRequest, "请求错误");
		}
	}

	@PatchMapping("/admin/{id}")
	public Object updateArticleInfo(@PathVariable("id") Integer id, @RequestBody Map<String, Object> map) {
		if (map.containsKey("op") && map.containsKey("path") && map.containsKey("value")) {
			UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", id);
			switch (map.get("path").toString())
			{
				case "/github":
					if (map.get("op").toString().equals("replace")) {
						updateWrapper.set("github", map.get("value"));
					}
					break;
				case "/gitee":
					if (map.get("op").toString().equals("replace")) {
						updateWrapper.set("gitee", map.get("value"));
					}
					break;
				default:
					return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "请求错误!");
			}
			if (service.update(updateWrapper)) {
				return new JsonResponse(HttpServletResponse.SC_OK, "更新成功!");
			} else {
				return new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "更新失败!");
			}
		} else {
			return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "请求错误！");
		}
	}
}
