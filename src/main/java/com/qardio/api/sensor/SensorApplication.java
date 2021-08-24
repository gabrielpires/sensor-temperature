package com.qardio.api.sensor;

import com.qardio.api.sensor.helpers.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SensorApplication {

    public static void main(String[] args) {
        Logger.setLoggerName("API");
        SpringApplication.run(SensorApplication.class, args);
    }

}
