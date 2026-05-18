package com.carplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main entry point for the Second-Hand Car Sales and Purchase Platform.
 * SE1020 – Object Oriented Programming Project
 *
 * @author Group (6 Members)
 * @version 1.0.0
 */
@SpringBootApplication
public class CarSalesPlatformApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CarSalesPlatformApplication.class, args);
    }
}
