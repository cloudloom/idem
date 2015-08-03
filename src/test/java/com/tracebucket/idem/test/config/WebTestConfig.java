package com.tracebucket.idem.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Vishwajit on 03-08-2015.
 */
@Configuration
@ComponentScan(basePackages = {"com.tracebucket.idem.service.impl"})
@EnableTransactionManagement(proxyTargetClass = true)
public class WebTestConfig {
}
