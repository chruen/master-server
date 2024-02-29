package com.swjtu.robot.masterserver.service.impl;

import com.swjtu.robot.masterserver.entity.Users;
import com.swjtu.robot.masterserver.mapper.UsersMapper;
import com.swjtu.robot.masterserver.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-29
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
