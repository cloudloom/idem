package com.tracebucket.idem.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingServiceBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ffl
 * @since 16-03-2015
 */
@Configuration
@Conditional(value = NonExistingServiceBeans.class)
@ComponentScan(basePackages = "com.tracebucket.idem.service.impl", scopedProxy = ScopedProxyMode.INTERFACES)
@EnableTransactionManagement(proxyTargetClass = true)
public class ServiceConfiguration {
}
