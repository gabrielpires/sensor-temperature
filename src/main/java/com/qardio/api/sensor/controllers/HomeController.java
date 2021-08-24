package com.qardio.api.sensor.controllers;

import com.qardio.api.sensor.enums.AggregationType;
import com.qardio.api.sensor.helpers.Logger;
import com.qardio.api.sensor.models.AbstractSensorLogAggregated;
import com.qardio.api.sensor.models.SensorLog;
import com.qardio.api.sensor.modules.SensorLogAggregatorBuilder;
import com.qardio.api.sensor.repositories.SensorLogAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogDailyAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogHourlyAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogRepository;
import com.qardio.api.sensor.requests.ListSensorLogsRequest;
import com.qardio.api.sensor.requests.SaveSensorLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Sensor controller.
 */
@RestController
public class HomeController {

    /**
     * heartbeat for validation of API
     *
     * @return the heartbeat string with datetime
     */
    @GetMapping("/")
    String heartbeat() {
        return String.format(
                "heartbeat: %s",
                (new Date()).toString()
        );
    }

}
