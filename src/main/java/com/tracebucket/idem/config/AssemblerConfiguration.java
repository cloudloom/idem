package com.tracebucket.idem.config;

import com.tracebucket.tron.context.EnableAutoAssemblerResolution;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Vishwajit on 16-04-2015.
 */
@Configuration
@EnableAutoAssemblerResolution(basePackages = {"com.tracebucket.idem.rest.assembler"})
public class AssemblerConfiguration {
}