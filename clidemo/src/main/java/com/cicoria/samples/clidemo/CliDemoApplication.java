package com.cicoria.samples.clidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class CliDemoApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(CliDemoApplication.class, args);
    }

}

