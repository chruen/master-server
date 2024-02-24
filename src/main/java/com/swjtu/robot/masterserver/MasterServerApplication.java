package com.swjtu.robot.masterserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 引导类，springboot项目的入口
 */
@SpringBootApplication
public class MasterServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MasterServerApplication.class, args);
        //var ioc  = SpringApplication.run(MasterServerApplication.class, args);
        // String[] beanNames = ioc.getBeanDefinitionNames();
        // List<String> s_beanNames = new ArrayList<>(List.of(beanNames));
        // Collections.sort(s_beanNames);
        // for (String name : s_beanNames){
        //     System.out.println("beanName = "+name.toString());
        // }
    }

}
