package com.jing.rbac.shiro.model;

import lombok.Data;

@Data
public class UserRegistModel {
    private String userId;
    private String name;
    private String mail;
    private Integer roleId;
}
