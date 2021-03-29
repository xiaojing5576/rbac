package com.jing.rbac.vo;

import lombok.Data;

import java.util.Set;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 15:44
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/
@Data
public class LoginResultVO {
    private String userId;
    private String userName;
    private Set<String> roleCodes;
    private Set<String> permissionCodes;
}
