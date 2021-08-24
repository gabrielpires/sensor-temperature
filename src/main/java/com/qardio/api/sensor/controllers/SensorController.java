package com.qardio.api.sensor.controllers;

import com.qardio.api.sensor.enums.AggregationType;
import com.qardio.api.sensor.helpers.Logger;
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
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Sensor controller.
 */
@RestController
public class SensorController {

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
     * @param listSensorLogs the list sensor logs
     * @return the list
     */
    @GetMapping("/log")
    List<? extends AbstractSensorLogAggregated> list(@RequestBody ListSensorLogsRequest listSensorLogs) {

        if (listSensorLogs.getAggregationType().equals(AggregationType.DAILY)) {
            return sensorLogDailyAggregatedRepository.findAll(
                    Sort.by(Sort.Direction.ASC, "when")
            );
        }

        return sensorLogHourlyAggregatedRepository.findAll(
                Sort.by(Sort.Direction.ASC, "when")
        );
    }

    /**
     * Save list.
     *
     * @param payload the payload
     * @return the list
     * @throws ParseException the parse exception
     */
    @PostMapping("/log")
    Map<String, Object> save(@RequestBody List<SaveSensorLogRequest> payload) throws ParseException {

        Map<String, Object> result = new HashMap<>();

        int logsToSave = payload.size();
        int logsSaved = 0;
        boolean aggregationGenerated = false;
        for (SaveSensorLogRequest entry : payload) {
            if (this.projectSensorLog(entry)) {
                logsSaved++;
            }
        }

        try {
            this.sensorLogAggregatorBuilder.build();
            aggregationGenerated = true;
        } catch (Exception exception) {
            Logger.exception("Unable to generate aggregation of sensor logs", exception);
        }

        result.put("logsToSave", logsToSave);
        result.put("logsSaved", logsSaved);
        result.put("aggregationGenerated", aggregationGenerated);

        return result;
    }

    /**
     * Project sensor log.
     *
     * @param payload the payload
     */
    private boolean projectSensorLog(SaveSensorLogRequest payload) throws ParseException {

        SensorLog log = payload.toSensorLog();

        try {
            repository.save(log);
            this.sensorLogAggregatorBuilder.add(log.when);
            return true;
        } catch (Exception exception) {
            Logger.exception("Unable to save sensor log", exception);
            return false;
        }

    }


}
