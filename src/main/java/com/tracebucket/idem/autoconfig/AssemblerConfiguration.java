package com.tracebucket.idem.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingAssemblerBeans;
import com.tracebucket.tron.context.EnableAutoAssemblerResolution;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Vishwajit on 16-04-2015.
 */
@Configuration
@Conditional(value = NonExistingAssemblerBeans.class)
@EnableAutoAssemblerResolution(basePackages = {"com.tracebucket.idem.rest.assembler"})
public class AssemblerConfiguration {
}