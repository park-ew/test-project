package com.parkew.test.testproject.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @auther parkew
 * @sinse 2023.09.12
 */
@Configuration
@EnableAutoConfiguration(exclude =  DataSourceAutoConfiguration.class)
public class BeanConfig {
}
