package com.fzcoder.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String nickname;

    private String avatar;

    private String homePage;

    private String github;

    private String gitee;

    private String motto;

    private String introduction;
}
