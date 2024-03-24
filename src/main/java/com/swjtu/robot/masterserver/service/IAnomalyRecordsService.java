package com.swjtu.robot.masterserver.service;

import com.swjtu.robot.masterserver.VO.Result;
import com.swjtu.robot.masterserver.entity.AnomalyRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-29
 */
public interface IAnomalyRecordsService extends IService<AnomalyRecords> {

    Result check_picture(MultipartFile file, int robotId, List<String> checkItems);
}
