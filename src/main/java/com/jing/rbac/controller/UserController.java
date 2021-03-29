package com.jing.rbac.controller;

import com.jing.rbac.response.ResponseResult;
import com.jing.rbac.response.ResultCode;
import com.jing.rbac.service.UserService;
import com.jing.rbac.vo.UserVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 15:56
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/

@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequiresPermissions("rbac:user:addUser")
    @PostMapping("/addUser")
    public ResponseResult<?> addUser(@RequestBody UserVO vo){
        userService.addUser(vo);
        return new ResponseResult<>(ResultCode.OK);

    }

    @RequiresPermissions("rabc:user:updateUserRole")
    @PostMapping("/updateUserRole")
    public ResponseResult<?> updateUserRole(@RequestBody UserVO vo){
        userService.updateUserRoles(vo);
        return new ResponseResult<>(ResultCode.OK);
    }


}
