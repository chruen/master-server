package com.swjtu.robot.masterserver.service.impl;

import com.swjtu.robot.masterserver.DTO.PictureDTO;
import com.swjtu.robot.masterserver.VO.Result;
import com.swjtu.robot.masterserver.entity.AnomalyRecords;
import com.swjtu.robot.masterserver.mapper.AnomalyRecordsMapper;
import com.swjtu.robot.masterserver.service.IAnomalyRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swjtu.robot.masterserver.utils.SystemConstants;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.swjtu.robot.masterserver.utils.SystemConstants.ROBOT_PICTURE_TTL;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-29
 */
@Slf4j
@Service
public class AnomalyRecordsServiceImpl extends ServiceImpl<AnomalyRecordsMapper, AnomalyRecords> implements IAnomalyRecordsService {

    //预处理队列
    private final BlockingQueue<PictureDTO> picturePreHandleQueue = new ArrayBlockingQueue<>(1024*1024);
    //请求Request队列
    private final BlockingQueue<PictureDTO> pictureRequestHandleQueue = new ArrayBlockingQueue<>(1024*1024);
    //后处理队列
    private final BlockingQueue<PictureDTO> pictureAfterHandleQueue = new ArrayBlockingQueue<>(1024*1024);

    //TODO:写到别的地方后
    private static final int picturePreHandleExecutorThreadNums = 1;
    private static final int pictureRequestHandleExecutorThreadNums =4;
    private static final int pictureAfterHandleExecutorThreadNums = 4;
    private static final int pictureSingleRequestHandleExecutorThreadNum = pictureRequestHandleExecutorThreadNums*3;
    //预处理线程池
    private static final ExecutorService picturePerHandlerExecutor = new ThreadPoolExecutor(picturePreHandleExecutorThreadNums,picturePreHandleExecutorThreadNums,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1024));
    //triton server请求线程池
    private static final ExecutorService pictureRequestHandlerExecutor = new ThreadPoolExecutor(pictureRequestHandleExecutorThreadNums,pictureRequestHandleExecutorThreadNums,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1024));
    //结果后处理线程池
    private static final ExecutorService pictureAfterHandlerExecutor = new ThreadPoolExecutor(pictureAfterHandleExecutorThreadNums,pictureAfterHandleExecutorThreadNums,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1024));
    //单次请求线程池
    private static final ExecutorService pictureSingleRequestHandlerExecutor = new ThreadPoolExecutor(pictureSingleRequestHandleExecutorThreadNum,pictureSingleRequestHandleExecutorThreadNum,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1024));


    @PostConstruct
    private void initHandler(){
        for (int i = 0; i < picturePreHandleExecutorThreadNums; i++) {
            picturePerHandlerExecutor.submit(new PicturePerHandler());
        }
        for (int i = 0; i < pictureRequestHandleExecutorThreadNums; i++) {
            pictureRequestHandlerExecutor.submit(new PictureRequestHandler());
        }
        for (int i = 0; i < pictureAfterHandleExecutorThreadNums; i++) {
            pictureAfterHandlerExecutor.submit(new PictureAfterHandler());
        }
    }

    public Result check_picture(MultipartFile file, int robotId, List<String> checkItems) {
        //打时间戳，并插入预处理线程池
        PictureDTO pictureDTO = new PictureDTO();
        pictureDTO.setFile(file);
        pictureDTO.setRobotId(robotId);
        pictureDTO.setCheck_items(checkItems);
        pictureDTO.setTime(System.currentTimeMillis());
        picturePreHandleQueue.add(pictureDTO);
        return Result.ok("Picture Upload Success");
    }

    private class PicturePerHandler implements Runnable{
        @Override
        public void run(){
            while(true){
                try {
                    //
                    PictureDTO pictureDTO = picturePreHandleQueue.take();
                    System.out.println("pre");
                    //TODO： 预处理

                    //预处理结束后塞入请求队列
                    pictureRequestHandleQueue.add(pictureDTO);
                } catch (InterruptedException e) {
                    log.error("获取预处理队列失败",e);
                }
            }
        }
    }

    private class PictureRequestHandler implements Runnable{
        @Override
        public void run(){
            while(true){
                try {
                    //
                    PictureDTO pictureDTO = pictureRequestHandleQueue.take();

                    //数据超时丢弃
                    if(System.currentTimeMillis()-pictureDTO.getTime()>ROBOT_PICTURE_TTL*1000){
                        continue;
                    }

                    //多线程请求多个模型
                    List<Future<String>> futures = new ArrayList<>();
                    for (String check_item : pictureDTO.getCheck_items()) {
                        Future<String> future = pictureSingleRequestHandlerExecutor.submit(new PictureSingleRequestHandler(check_item, pictureDTO));
                        futures.add(future);
                    }
                    //等待获取所有模型的推理结果
                    for (Future<String> future : futures) {
                        String s = future.get();
                        //TODO：整理结果
                    }
                    pictureDTO.setResult("abc");

                    //System.out.println("request");
                    //请求结束后塞入请求队列
                    pictureAfterHandleQueue.add(pictureDTO);
                } catch (InterruptedException e) {
                    log.error("获取请求队列失败",e);
                } catch (ExecutionException e) {
                    log.error("获取结果失败",e);
                }
            }
        }
    }

    private class PictureAfterHandler implements Runnable{
        @Override
        public void run(){
            while(true){
                try {
                    //
                    PictureDTO pictureDTO = pictureAfterHandleQueue.take();
                    System.out.println("after");
                    //TODO：查看分析结果

                    //TODO：根据ID通知机器人

                    //TODO:画框

                    //TODO：存数据库

                    //TODO：存图

                } catch (InterruptedException e) {
                    log.error("获取后处理队列失败",e);
                }
            }
        }
    }

    private class PictureSingleRequestHandler implements Callable<String>{

        String checkItem;
        PictureDTO pictureDTO;
        PictureSingleRequestHandler(String checkItem, PictureDTO pictureDTO) {
            this.checkItem = checkItem;
            this.pictureDTO = pictureDTO;
        }

        @Override
        public String call() throws Exception {
            //TODO：发送请求
            return null;
        }
    }
}
