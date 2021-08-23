package com.qardio.api.sensor.payloads;

import java.util.Date;

/**
 * SaveSensorLog is a class to deserialization payload for POST /temperature
 */
public class SaveSensorLog {

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
    public SaveSensorLog(){

    }

    /**
     * Class constructor with all properties
     *
     * @param temperature - is the value in celsius
     * @param when        is the date when the measure occur
     */
    public SaveSensorLog(Float temperature, Date when) {
        this.temperature = temperature;
        this.when = when;
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
