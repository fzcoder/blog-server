package com.frankfang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frankfang.entity.User;
import com.frankfang.mapper.UserMapper;
import com.frankfang.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserDetailsService, UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		// 设置查询字段
		String[] sqlSelect = {"id", "username", "password", "enabled", "locked"};
		queryWrapper.select(sqlSelect);
		queryWrapper.eq(true, "username", username);
		User user = userMapper.selectOne(queryWrapper);
		if (user == null) {
			throw new UsernameNotFoundException("账户不存在！");
		}
		user.setRoles(userMapper.selectRolesByUid(user.getId()));
		return user;
	}
}
