package com.gas;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
@MapperScan("com.gas.dao")
@Slf4j
public class SynDataApplication implements CommandLineRunner {

    @Value("${server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(SynDataApplication.class, args);
    }

    @Override
    public void run(String... args) {

        log.info("启动完成，访问地址如下：\n");
        String hostname = "localhost";
        log.info("Api文档访问地址如下：\n");
        log.info("http://" + hostname + ":" + port + "/doc.html");
    }

}
