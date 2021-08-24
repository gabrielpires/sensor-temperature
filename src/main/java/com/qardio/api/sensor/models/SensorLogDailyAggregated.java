package com.qardio.api.sensor.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * The type Sensor log daily aggregated.
 */
@Document(collection = "sensorLogDailyAggregated")
public class SensorLogDailyAggregated extends AbstractSensorLogAggregated {
    /**
     * Instantiates a new Sensor log daily aggregated.
     *
     * @param when               the when
     * @param averageTemperature the average temperature
     * @param totalRecords       the total records
     */
    public SensorLogDailyAggregated(Date when, Float averageTemperature, Integer totalRecords) {
        super(when, averageTemperature, totalRecords);
    }
}
