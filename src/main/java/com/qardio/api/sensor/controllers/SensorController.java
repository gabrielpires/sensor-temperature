package com.qardio.api.sensor.controllers;

import com.qardio.api.sensor.models.SensorLog;
import com.qardio.api.sensor.models.SensorLogAggregated;
import com.qardio.api.sensor.payloads.ListSensorLogs;
import com.qardio.api.sensor.payloads.SaveSensorLog;
import com.qardio.api.sensor.repositories.SensorLogAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogDailyAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogHourlyAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The type Sensor controller.
 */
@RestController
class SensorController {

    @Autowired
    private SensorLogRepository repository;

    @Autowired
    private SensorLogAggregatedRepository sensorLogAggregateRepository;

    @Autowired
    private SensorLogDailyAggregatedRepository sensorLogDailyAggregatedRepository;

    @Autowired
    private SensorLogHourlyAggregatedRepository sensorLogHourlyAggregatedRepository;

    /**
     * All list.
     *
     * @return the list
     */
    @GetMapping("/log")
    List<SensorLogAggregated> all(@RequestBody ListSensorLogs listSensorLogs) {

        return sensorLogAggregateRepository.listSensorLogAggregated(
                listSensorLogs.getAggregation(),
                listSensorLogs.getFrom(),
                listSensorLogs.getTo()
        );
    }

    /**
     * Save list.
     *
     * @param payload the payload
     * @return the list
     */
    @PostMapping("/log")
    List<SensorLog> save(@RequestBody List<SaveSensorLog> payload) {

        payload.forEach(entry -> repository.save(getSensorLog(entry)));


        //generate hourly aggregation
        List<SensorLogAggregated> hourlyAggregated = sensorLogAggregateRepository.listSensorLogAggregated("HOURLY");
        hourlyAggregated.forEach(entry -> sensorLogHourlyAggregatedRepository.save(entry));

        //generate daily aggregation
        List<SensorLogAggregated> dailyAggregated = sensorLogAggregateRepository.listSensorLogAggregated("DAILY");
        dailyAggregated.forEach(entry -> sensorLogDailyAggregatedRepository.save(entry));

        return null;
    }

    /**
     * getSensorLog is a method to convert the incoming payload into the model SensorLog
     *
     * @param payload - is an instance of SaveSensorLog
     * @return SensorLog
     * @see SensorLog
     */
    private SensorLog getSensorLog(SaveSensorLog payload) {

        return new SensorLog(
                UUID.randomUUID().toString(),
                payload.getTemperature(),
                payload.getWhen()
        );

    }

}
