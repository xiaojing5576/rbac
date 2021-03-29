package com.jing.rbac.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jing.rbac.common.Constants;
import com.jing.rbac.dao.UserMapper;
import com.jing.rbac.dao.UserRoleMapper;
import com.jing.rbac.domain.Role;
import com.jing.rbac.domain.User;
import com.jing.rbac.domain.UserRole;
import com.jing.rbac.shiro.model.UserRegistModel;
import com.jing.rbac.shiro.util.CasClient;
import com.jing.rbac.util.SnowFlakeUtil;
import com.jing.rbac.vo.LoginResultVO;
import com.jing.rbac.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 15:02
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private CasClient casClient;

    @Resource
    private RoleService roleService;

    public Set<String> getUserRoles(String userId){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id",userId));
        if(user == null){
            return new HashSet<>();
        }
        List<UserRole> userRoles = userRoleMapper.selectList(new QueryWrapper<UserRole>()
                .eq("user_id",userId).eq("status", Constants.status.NORMAL.getCode()));
        List<String> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Role> roles = roleService.list(new QueryWrapper<Role>().in("role_id",roleIds));
        Set<String> roleCodes = new HashSet<String>();
        roleCodes.addAll(roles.stream().map(Role::getRoleCode).collect(Collectors.toList()));
        return roleCodes;
    }

    public LoginResultVO getLoginInfo(String userId){
        LoginResultVO model = new LoginResultVO();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id",userId));
        if(user == null){
            return null;
        }
        model.setUserId(userId);
        model.setUserName(user.getUserName());
        Set<String> roles = getUserRoles(userId);
        model.setRoleCodes(roles);
        Set<String> permissions = new HashSet<>();
        roles.forEach(x->{
            permissions.addAll(roleService.getRolePermissions(x));
        });
        model.setPermissionCodes(permissions);
        return model;
    }

    public void addUser(UserVO vo){
        //1.新增用户信息
        User user = new User();
        user.setId(SnowFlakeUtil.nextId());
        BeanUtils.copyProperties(vo,user);
        this.save(user);
        //2.建立用户角色联系
        List<Role> roles = roleService.list(new QueryWrapper<Role>().in("role_code",vo.getRoleCode()));
        roles.forEach(x->{
            UserRole userRole = new UserRole();
            userRole.setId(SnowFlakeUtil.nextId());
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(x.getRoleId());
            userRoleMapper.insert(userRole);
        });
        //3.cas中注册
        String token = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
        UserRegistModel registModel = new UserRegistModel();
        registModel.setUserId(user.getUserId());
        registModel.setName(user.getUserName());
        registModel.setMail(user.getMail());
        registModel.setRoleId(1);
        casClient.addCasUser(token,registModel,vo.getRoleCode().contains("admin"));
    }


    public void updateUserRoles(UserVO userVO){
        List<Role> roles = roleService.list(new QueryWrapper<Role>().eq("role_code",userVO.getRoleCode()));
        List<String> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id",userVO.getUserId()));
        roleIds.forEach(x->{
            UserRole userRole = new UserRole();
            userRole.setId(SnowFlakeUtil.nextId());
            userRole.setRoleId(x);
            userRole.setUserId(userVO.getUserId());
            userRoleMapper.insert(userRole);
        });
    }

}
