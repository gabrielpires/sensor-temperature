package com.qardio.api.sensor;

import com.qardio.api.sensor.controllers.SensorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SensorApplicationTests {

	@Autowired
	private SensorController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
