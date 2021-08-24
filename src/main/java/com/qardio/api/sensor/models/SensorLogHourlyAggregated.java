package com.qardio.api.sensor.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * The type Sensor log hourly aggregated.
 */
@Document(collection = "sensorLogHourlyAggregated")
public class SensorLogHourlyAggregated extends AbstractSensorLogAggregated {
    /**
     * Instantiates a new Sensor log hourly aggregated.
     *
     * @param when               the when
     * @param averageTemperature the average temperature
     * @param totalRecords       the total records
     */
    public SensorLogHourlyAggregated(Date when, Float averageTemperature, Integer totalRecords) {
        super(when, averageTemperature, totalRecords);
    }
}
