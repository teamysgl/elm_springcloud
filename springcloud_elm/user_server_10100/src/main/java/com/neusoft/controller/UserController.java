package com.neusoft.controller;

import com.neusoft.po.CommonResult;
import com.neusoft.po.User;
import com.neusoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping("/UserController")
public class UserController {

    private final UserServiceAdapter userServiceAdapter;

    @Autowired
    public UserController(UserServiceAdapter userServiceAdapter) {
        this.userServiceAdapter = userServiceAdapter;
    }

    @GetMapping("/getUserByIdByPass/{userId}/{password}")
    public CommonResult<User> getUserByIdByPass(
            @PathVariable("userId") String userId,
            @PathVariable("password") String password
    ) throws Exception {
        return userServiceAdapter.getUserByIdByPass(userId, password);
    }

    @GetMapping("/getUserById/{userId}")
    public CommonResult<Integer> getUserById(@PathVariable("userId") String userId) throws Exception {
        return userServiceAdapter.getUserById(userId);
    }

    @PostMapping("/saveUser/{userId}/{password}/{userName}/{userSex}")
    public CommonResult<Integer> saveUser(
            @PathVariable("userId") String userId,
            @PathVariable("password") String password,
            @PathVariable("userName") String userName,
            @PathVariable("userSex") Integer userSex
    ) throws Exception {
        return userServiceAdapter.saveUser(userId, password, userName, userSex);
    }
}
