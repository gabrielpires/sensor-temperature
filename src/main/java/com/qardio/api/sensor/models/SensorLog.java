package com.qardio.api.sensor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * The type Sensor log.
 */
@Document(collection = "sensorLog")
public class SensorLog {

    /**
     * The Id.
     */
    @Id
    public String id;

    /**
     * The When.
     */
    public Date when;

    /**
     * The Temperature.
     */
    public Float temperature;

    /**
     * Instantiates a new Sensor log.
     */
    public SensorLog() {
    }

    /**
     * Instantiates a new Sensor log.
     *
     * @param when        the when
     * @param temperature the temperature
     */
    public SensorLog(Date when, Float temperature) {
        this.when = when;
        this.temperature = temperature;
    }

    /**
     * Instantiates a new Sensor log.
     *
     * @param id          the id
     * @param when        the when
     * @param temperature the temperature
     */
    public SensorLog(String id, Date when, Float temperature) {
        this.id = id;
        this.when = when;
        this.temperature = temperature;
    }


}
