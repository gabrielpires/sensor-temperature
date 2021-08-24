package com.qardio.api.sensor.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "sensorLogHourlyAggregated")
public class SensorLogHourlyAggregated extends AbstractSensorLogAggregated {
        public SensorLogHourlyAggregated(Date when, Float averageTemperature, Integer totalRecords) {
                super(when, averageTemperature, totalRecords);
        }
}
