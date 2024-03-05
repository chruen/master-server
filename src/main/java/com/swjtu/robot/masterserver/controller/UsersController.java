package com.swjtu.robot.masterserver.controller;


import com.swjtu.robot.masterserver.VO.LoginFromVO;
import com.swjtu.robot.masterserver.VO.Result;
import com.swjtu.robot.masterserver.entity.Users;
import com.swjtu.robot.masterserver.service.IUsersService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public Result login(@RequestBody LoginFromVO loginFrom, HttpSession session){
        return usersService.login(loginFrom, session);
    }

    @PostMapping("/sign")
    public Result signUp(@RequestBody Users user,HttpSession session){
        return usersService.signUp(user,session);
    }

}
