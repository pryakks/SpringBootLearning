package com.employee.management.actuator.services;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class LoggerService  implements HealthIndicator {

    private final String LOGGER_SERVICE ="Database Service";
    @Override
    public Health health() {
        if(isLoggerServiceHealthGood()){
            return Health.up().withDetail(LOGGER_SERVICE, "Service is running").build();
        }
        return Health.down().withDetail(LOGGER_SERVICE, "Service is not available").build();
    }

    private boolean isLoggerServiceHealthGood(){
        //logic
        return false;
    }
}

