package com.cicoria.samples.clidemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class CliDemoApplication {
    private static final Logger log = LoggerFactory.getLogger(CliDemoApplication.class);
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(CliDemoApplication.class, args); //.close();
        log.info("done...");
        context.close();
    }

}

