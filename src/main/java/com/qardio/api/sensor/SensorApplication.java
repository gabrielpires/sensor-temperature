package com.qardio.api.sensor;

import com.qardio.api.sensor.helpers.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Sensor application.
 */
@SpringBootApplication
public class SensorApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Logger.setLoggerName("API");
        SpringApplication.run(SensorApplication.class, args);
    }

}
