package com.frankfang.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserView implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id，作为唯一的表示")
    private Integer userId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "个人主页")
    private String homePage;

    @ApiModelProperty(value = "Github账号")
    private String github;

    @ApiModelProperty(value = "码云账号")
    private String gitee;

    @ApiModelProperty(value = "座右铭")
    private String motto;

    @ApiModelProperty(value = "简介")
    private String introduction;
}
