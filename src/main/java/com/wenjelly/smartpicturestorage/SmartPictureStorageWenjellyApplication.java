package com.wenjelly.smartpicturestorage;

import org.apache.shardingsphere.spring.boot.ShardingSphereAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan("com.wenjelly.smartpicturestorage.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication(exclude = {ShardingSphereAutoConfiguration.class})
public class SmartPictureStorageWenjellyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartPictureStorageWenjellyApplication.class, args);
    }

}
