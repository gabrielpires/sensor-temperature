package com.qardio.api.sensor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * The type Abstract sensor log aggregated.
 */
public abstract class AbstractSensorLogAggregated {


    /**
     * The Id.
     */
    @Id
    public String id;

    /**
     * The When.
     */
    @Indexed(unique = true)
    public Date when;

    /**
     * The Average temperature.
     */
    public Float averageTemperature;

    /**
     * The Total records.
     */
    public Integer totalRecords;

    /**
     * Instantiates a new Abstract sensor log aggregated.
     */
    public AbstractSensorLogAggregated() {
        this(null, null, null);
    }

    /**
     * Instantiates a new Abstract sensor log aggregated.
     *
     * @param when               the when
     * @param averageTemperature the average temperature
     * @param totalRecords       the total records
     */
    public AbstractSensorLogAggregated(Date when, Float averageTemperature, Integer totalRecords) {
        this.when = when;
        this.averageTemperature = averageTemperature;
        this.totalRecords = totalRecords;
    }

    /**
     * Instantiates a new Abstract sensor log aggregated.
     *
     * @param id                 the id
     * @param when               the when
     * @param averageTemperature the average temperature
     * @param totalRecords       the total records
     */
    public AbstractSensorLogAggregated(String id, Date when, Float averageTemperature, Integer totalRecords) {
        this.id = id;
        this.when = when;
        this.averageTemperature = averageTemperature;
        this.totalRecords = totalRecords;
    }

}
