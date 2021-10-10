package com.fzcoder.opensource.blog.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzcoder.opensource.blog.entity.Role;
import com.fzcoder.opensource.blog.entity.User;
import com.fzcoder.opensource.blog.vo.UserView;

public interface IUserMapper extends BaseMapper<User> {

	List<Role> selectRolesByUid(Integer uid);

	UserView selectViewObjectById(Integer uid);

}
