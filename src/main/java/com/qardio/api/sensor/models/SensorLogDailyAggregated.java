package com.qardio.api.sensor.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "sensorLogDailyAggregated")
public class SensorLogDailyAggregated extends AbstractSensorLogAggregated {
        public SensorLogDailyAggregated(Date when, Float averageTemperature, Integer totalRecords) {
                super(when, averageTemperature, totalRecords);
        }
}
