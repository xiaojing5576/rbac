package com.jing.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jing.rbac.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 14:21
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
