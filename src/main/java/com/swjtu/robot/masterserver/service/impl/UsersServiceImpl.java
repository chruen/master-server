package com.swjtu.robot.masterserver.service.impl;

import cn.hutool.core.lang.UUID;
import com.swjtu.robot.masterserver.VO.LoginFromVO;
import com.swjtu.robot.masterserver.VO.Result;
import com.swjtu.robot.masterserver.VO.SignUpVo;
import com.swjtu.robot.masterserver.entity.Users;
import com.swjtu.robot.masterserver.mapper.UsersMapper;
import com.swjtu.robot.masterserver.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swjtu.robot.masterserver.utils.PasswordEncoder;
import com.swjtu.robot.masterserver.utils.UserIdFactory;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.swjtu.robot.masterserver.utils.RedisConstants.USER_TOKEN;
import static com.swjtu.robot.masterserver.utils.RedisConstants.USER_TOKEN_TTL;

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

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(LoginFromVO login, HttpSession session) {
        Users user = getById(login.getUserId());
        if(user==null){
            return Result.fail("用户不存在");
        }
        boolean verifyPassword = PasswordEncoder.verifyPassword(login.getPassword(), user.getPassword());
        if(!verifyPassword){
            return Result.fail("密码错误，请重试");
        }
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(USER_TOKEN+user.getId().toString(),uuid,USER_TOKEN_TTL, TimeUnit.MINUTES);
        return Result.ok(uuid);
    }

    @Override
    public Result signUp(Users user, HttpSession session) {
        //TODO:检查手机号，密码长度等

        user.setId(new UserIdFactory(stringRedisTemplate).getNextId());
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        save(user);
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(USER_TOKEN+user.getId().toString(),uuid,USER_TOKEN_TTL, TimeUnit.MINUTES);
        SignUpVo userVo = new SignUpVo();
        userVo.setUserId(user.getId());
        userVo.setToken(uuid);
        return Result.ok(userVo);
    }
}
