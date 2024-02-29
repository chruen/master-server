package com.swjtu.robot.masterserver.service.impl;

import com.swjtu.robot.masterserver.entity.Point;
import com.swjtu.robot.masterserver.mapper.PointMapper;
import com.swjtu.robot.masterserver.service.IPointService;
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
public class PointServiceImpl extends ServiceImpl<PointMapper, Point> implements IPointService {

}
