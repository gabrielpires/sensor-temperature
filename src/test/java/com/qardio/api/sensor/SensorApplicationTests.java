package com.qardio.api.sensor;


import com.qardio.api.sensor.controllers.SensorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Sensor application tests.
 */
@SpringBootTest
class SensorApplicationTests {


    @Autowired
    private SensorController controller;

    /**
     * Context loads.
     */
    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
