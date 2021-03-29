package com.jing.rbac.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 14:44
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/

@TableName("role_permission")
@Data
public class RolePermission {

    @TableId("id")
    private Long id;

    @TableField("role_id")
    private String roleId;

    @TableField("permission_id")
    private String permissionId;
}
