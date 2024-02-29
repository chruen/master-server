package com.swjtu.robot.masterserver.service.impl;

import com.swjtu.robot.masterserver.entity.Task;
import com.swjtu.robot.masterserver.mapper.TaskMapper;
import com.swjtu.robot.masterserver.service.ITaskService;
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
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

}
