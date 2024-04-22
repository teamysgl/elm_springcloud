//适配器模式 
/*现在UserController只关注于处理HTTP请求和返回结果，而不需要直接与UserService交互。同时，UserServiceAdapter封装了与UserService的交互，并提供了符合UserController的接口。*/

package com.neusoft.controller;

import com.neusoft.po.CommonResult;
import com.neusoft.po.User;
import com.neusoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceAdapter {

    private final UserService userService;

    @Autowired
    public UserServiceAdapter(UserService userService) {
        this.userService = userService;
    }

    public CommonResult<User> getUserByIdByPass(String userId, String password) throws Exception {
        User param = new User();
        param.setUserId(userId);
        param.setPassword(password);
        User user = userService.getUserByIdByPass(param);
        return new CommonResult<>(200, "success", user);
    }

    public CommonResult<Integer> getUserById(String userId) throws Exception {
        int result = userService.getUserById(userId);
        return new CommonResult<>(200, "success", result);
    }

    public CommonResult<Integer> saveUser(String userId, String password, String userName, Integer userSex) throws Exception {
        User param = new User();
        param.setUserId(userId);
        param.setPassword(password);
        param.setUserName(userName);
        param.setUserSex(userSex);
        int result = userService.saveUser(param);
        return new CommonResult<>(200, "success", result);
    }
}
