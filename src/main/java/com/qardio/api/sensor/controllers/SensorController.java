package com.qardio.api.sensor.controllers;

import com.qardio.api.sensor.enums.AggregationType;
import com.qardio.api.sensor.models.AbstractSensorLogAggregated;
import com.qardio.api.sensor.models.SensorLog;
import com.qardio.api.sensor.modules.SensorLogAggregatorBuilder;
import com.qardio.api.sensor.requests.ListSensorLogsRequest;
import com.qardio.api.sensor.requests.SaveSensorLogRequest;
import com.qardio.api.sensor.repositories.SensorLogAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogDailyAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogHourlyAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

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

    @Autowired
    private SensorLogAggregatorBuilder sensorLogAggregatorBuilder;

    /**
     * All list.
     *
     * @return the list
     */
    @GetMapping("/log")
    List<? extends AbstractSensorLogAggregated> all(@RequestBody ListSensorLogsRequest listSensorLogs) {

        if(listSensorLogs.getAggregationType().equals(AggregationType.DAILY)){
            return sensorLogDailyAggregatedRepository.findAll();
        }

        return sensorLogHourlyAggregatedRepository.findAll();
    }

    /**
     * Save list.
     *
     * @param payload the payload
     * @return the list
     */
    @PostMapping("/log")
    List<SensorLog> save(@RequestBody List<SaveSensorLogRequest> payload) throws ParseException {

        for(SaveSensorLogRequest entry : payload){
            this.projectSensorLog(entry);
        }

        this.sensorLogAggregatorBuilder.build();

        return null;
    }

    /**
     * Project sensor log.
     *
     * @param payload the payload
     */
    private void projectSensorLog(SaveSensorLogRequest payload) throws ParseException {

        SensorLog log = payload.toSensorLog();

        repository.save(log);

        this.sensorLogAggregatorBuilder.add(log.when);
    }


}
