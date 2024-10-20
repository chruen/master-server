package com.swjtu.robot.masterserver.config;

import com.swjtu.robot.masterserver.VO.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Web异常
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {

    /**
     * RuntimeException
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        log.error(e.toString(), e);
        return Result.fail("Server Error");
    }
}
