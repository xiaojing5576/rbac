package com.jing.rbac.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jing.rbac.config.LongSerializer;
import lombok.Data;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 14:26
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/
@Data
@TableName("role")
public class Role {

    @TableId("id")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @TableField("role_id")
    private String roleId;

    @TableField("role_code")
    private String roleCode;

    @TableField("role_name")
    private String roleName;

    @TableField("status")
    private String status;

}
