package com.swjtu.robot.masterserver.controller;


import com.swjtu.robot.masterserver.VO.Result;
import com.swjtu.robot.masterserver.service.IAnomalyRecordsService;
import com.swjtu.robot.masterserver.service.impl.AnomalyRecordsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-02-29
 */
@RestController
@RequestMapping("/anomaly-records")
public class AnomalyRecordsController {

    @Resource
    private IAnomalyRecordsService anomalyRecordsService;

    @PostMapping("/check_picture/{robotId}/")
    public Result check_picture(@RequestParam("file") MultipartFile file,
                                @PathVariable("robotId") int robotId,
                                @RequestParam("checkItems") List<String> checkItems){
        //TODO
        return anomalyRecordsService.check_picture(file,robotId,checkItems);
    }
}
