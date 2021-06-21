package com.krakatoa.distributioncalculator.configuration;

import com.krakatoa.distributioncalculator.DistributionCalculatorApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

    /**
     * This loggingHelper was not ultimately used throughout this code
     * but figured it would be helpful to have
     *
     * @return Logger
     */
    @Bean
    public Logger loggingHelper() {
        return LoggerFactory.getLogger(DistributionCalculatorApplication.class);
    }

}
