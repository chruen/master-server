package com.swjtu.robot.masterserver.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PictureDTO {
    private MultipartFile file;
    private int robotId;
    private List<String> check_items;
    private long time;
    private String result;
}
