package com.fzcoder.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzcoder.entity.Role;
import com.fzcoder.entity.User;
import com.fzcoder.view.UserView;

public interface UserMapper extends BaseMapper<User> {

	List<Role> selectRolesByUid(Integer uid);

	UserView selectViewObjectById(Integer uid);

}
