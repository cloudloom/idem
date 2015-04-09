package com.tracebucket.idem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author FFL
 * @Since 11-03-2015
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.tracebucket.idem.config")
public class IdemStarter {
    public static void main(String[] args) {
        SpringApplication.run(IdemStarter.class, args);
    }
}
