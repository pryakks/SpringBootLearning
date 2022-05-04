package com.employee.management.actuator.services;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class DatabaseService implements HealthIndicator {

    private final String DATABASE_SERVICE ="Database Service";
    @Override
    public Health health() {
        if(isDataBaseHealthGood()){
            return Health.up().withDetail(DATABASE_SERVICE, "Service is running").build();
        }
        return Health.down().withDetail(DATABASE_SERVICE, "Service is not available").build();
    }

    private boolean isDataBaseHealthGood(){
        //logic
        return true;
    }
}
