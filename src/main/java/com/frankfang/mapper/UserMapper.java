package com.frankfang.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankfang.entity.Role;
import com.frankfang.entity.User;

public interface UserMapper extends BaseMapper<User> {

	List<Role> selectRolesByUid(Integer uid);

}
