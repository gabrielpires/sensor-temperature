package com.qardio.api.sensor.requests;

import com.qardio.api.sensor.models.SensorLog;

import java.util.Date;

/**
 * SaveSensorLogRequest is a class to deserialization payload for POST /log
 */
public class SaveSensorLogRequest {

    /**
     * Temperature value, expected in CELSIUS
     */
    private Float temperature;

    /**
     * When value, is when the measure took place
     */
    private Date when;

    /**
     * Class default constructor
     */
    public SaveSensorLogRequest() {

    }

    /**
     * Class constructor with all properties
     *
     * @param temperature - is the value in celsius
     * @param when        is the date when the measure occur
     */
    public SaveSensorLogRequest(Float temperature, Date when) {
        this.temperature = temperature;
        this.when = when;
    }


    /**
     * To sensor log sensor log.
     *
     * @return the sensor log
     * @see SensorLog
     */
    public SensorLog toSensorLog() {
        return new SensorLog(this.when, this.temperature);
    }

    /**
     * Gets temperature.
     *
     * @return the temperature
     */
    public Float getTemperature() {
        return temperature;
    }

    /**
     * Sets temperature.
     *
     * @param temperature - is the value in celsius
     */
    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    /**
     * Gets when.
     *
     * @return the when
     */
    public Date getWhen() {
        return when;
    }

    /**
     * Sets when.
     *
     * @param when - is the date when the measure occur
     */
    public void setWhen(Date when) {
        this.when = when;
    }

}
