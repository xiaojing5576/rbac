package com.jing.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jing.rbac.domain.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 15:00
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
