package com.jing.rbac.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jing.rbac.dao.PermissionMapper;
import com.jing.rbac.domain.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 15:03
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/
@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

    @Resource
    private PermissionMapper permissionMapper;

}
