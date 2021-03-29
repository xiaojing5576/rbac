package com.jing.rbac.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jing.rbac.common.Constants;
import com.jing.rbac.dao.RoleMapper;
import com.jing.rbac.dao.RolePermissionMapper;
import com.jing.rbac.domain.Permission;
import com.jing.rbac.domain.Role;
import com.jing.rbac.domain.RolePermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 15:03
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private PermissionService permissionService;

    public Set<String> getRolePermissions(String roleCode){
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("role_code",roleCode));
        if(role == null){
            return new HashSet<>();
        }

        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>()
                .eq("role_id",role.getId()).eq("status", Constants.status.NORMAL.getCode()));
        List<String> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        List<Permission> permissions = permissionService.list(new QueryWrapper<Permission>().in("permission_id",permissionIds));
        Set<String> permissionCodes = new HashSet<>();
        permissionCodes.addAll(permissions.stream().map(Permission::getPermissionCode).collect(Collectors.toList()));
        return permissionCodes;
    }

}
