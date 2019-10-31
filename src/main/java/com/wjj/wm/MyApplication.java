package com.wjj.wm;

import com.wjj.wm.common.utils.SnowFlakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 * @ClassName MyApplication
 * @Description 启动类
 * @Author weng_jun_ji_
 * @Date 2019/9/23 11:43
 * @Vervsion 1.0
 */
@SpringBootApplication
@MapperScan("com.wjj.wm.mapper")
//@ImportResource("classpath:spring/*.xml")
@Slf4j
public class MyApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    //启动时执行该方法
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("启动时执行...");
        createSnowFlakeBean();
    }

    //注入雪花算法
    @Bean
    public SnowFlakeIdWorker createSnowFlakeBean() {
        return new SnowFlakeIdWorker(1L, 1L);
    }
}
