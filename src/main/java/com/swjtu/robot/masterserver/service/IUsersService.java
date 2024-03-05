package com.swjtu.robot.masterserver.service;

import com.swjtu.robot.masterserver.VO.LoginFromVO;
import com.swjtu.robot.masterserver.VO.Result;
import com.swjtu.robot.masterserver.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-29
 */
public interface IUsersService extends IService<Users> {
    Result login(LoginFromVO login, HttpSession session);

    Result signUp(Users user, HttpSession session);
}
