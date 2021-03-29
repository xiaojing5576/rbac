package com.jing.rbac.vo;

import lombok.Data;

import java.util.Set;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 16:26
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/
@Data
public class UserVO {

    private String userName;

    private String userId;

    private String tel;

    private String mail;

    private String des;

    private Set<String> roleCode;

}
