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
@TableName("permission")
public class Permission {

    @TableId("id")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    private String permissionId;

    @TableField("permission_code")
    private String permissionCode;

    @TableField("permission_name")
    private String permissionName;

    @TableField("status")
    private String status;

    @TableField("des")
    private String des;
}
