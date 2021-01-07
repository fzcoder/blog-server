package com.fzcoder.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user")
@ApiModel(value="User对象", description="")
public class User implements UserDetails, Serializable{

	/**
	 * 实现序列化接口
	 */
	private static final long serialVersionUID = 9067364805995616069L;

	@ApiModelProperty(value = "id，作为唯一的表示")
    @TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	@ApiModelProperty(value = "用户名，不可重复")
    @TableField("username")
	private String username;

	@ApiModelProperty(value = "昵称")
	@TableField("nickname")
	private String nickname;

	@ApiModelProperty(value = "用户头像")
	@TableField("avatar")
	private String avatar;
	
	@ApiModelProperty(value = "绑定邮箱")
    @TableField("email")
	private String email;

	@ApiModelProperty(value = "密码")
	@TableField("home_page")
	private String homePage;

	@ApiModelProperty(value = "密码")
	@TableField("github")
	private String github;

	@ApiModelProperty(value = "密码")
	@TableField("gitee")
	private String gitee;
	
	@ApiModelProperty(value = "密码")
    @TableField("password")
	private String password;

	@ApiModelProperty(value = "座右铭")
	@TableField("motto")
	private String motto;

	@ApiModelProperty(value = "简介")
	@TableField("introduction")
	private String introduction;
	
	@ApiModelProperty(value = "是否被禁用")
    @TableField("enabled")
	private Boolean enabled;
	
	@ApiModelProperty(value = "是否被锁定")
    @TableField("locked")
	private Boolean locked;

	@ApiModelProperty(value = "用户角色信息")
	private List<Role> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomePage() {
		return this.homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getGithub() {
		return this.github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public String getGitee() {
		return this.gitee;
	}

	public void setGitee(String gitee) {
		this.gitee = gitee;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean isLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for(Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	//判断当前帐户是否过期
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//判断当前帐户是否未锁定
	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	//判断当前账户密码是否未过期
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//判断当前账号是否可用
	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
