package com.swjtu.robot.masterserver.controller;


import com.swjtu.robot.masterserver.VO.Result;
import com.swjtu.robot.masterserver.service.IUsersService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-02-29
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Resource
    IUsersService usersService;

    @GetMapping("/{id}")
    public Result getByUserId(@PathVariable("id") Long id){
        return Result.ok(usersService.getById(id));
    }
}
