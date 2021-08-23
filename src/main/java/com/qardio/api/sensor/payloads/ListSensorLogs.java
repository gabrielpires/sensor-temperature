package com.qardio.api.sensor.payloads;

import com.qardio.api.sensor.payloads.exceptions.AggregationTypeNotAllowedException;

import java.util.ArrayList;
import java.util.Date;

/**
 * SaveSensorLog is a class to deserialization payload for POST /temperature
 */
public class ListSensorLogs {

    /**
     * List of allowed aggregation types
     */
    private final ArrayList<String> allowedAggregations = new ArrayList<>(2){{
        add("HOURLY");
        add("DAILY");
    }};

    /**
     * Aggregation is the type of aggregation for the returned list
     * The value can be DAILY or HOURLY
     */
    private String aggregation;

    /**
     * from is the initial date for filtering the sensor records
     */
    private Date from;

    /**
     * from is the date date for filtering the sensor records
     */
    private Date to;

    /**
     * Class default constructor
     */
    public ListSensorLogs() {
    }

    /**
     * Instantiates a new List sensor logs.
     *
     * @param aggregation the aggregation
     * @param from        the from
     * @param to          the to
     */
    public ListSensorLogs(String aggregation, Date from, Date to) throws AggregationTypeNotAllowedException {
        this.aggregation = aggregation;
        this.from = from;
        this.to = to;

        this.validateAggregationType();
    }

    private void validateAggregationType() throws AggregationTypeNotAllowedException {
        if(this.allowedAggregations.contains(this.aggregation)){
            return;
        }

        throw new AggregationTypeNotAllowedException(this.aggregation);
    }


    /**
     * Gets aggregation.
     *
     * @return the aggregation
     */
    public String getAggregation() {
        return aggregation;
    }


    /**
     * Sets aggregation.
     *
     * @param aggregation the aggregation
     * @throws AggregationTypeNotAllowedException the aggregation type not allowed exception
     */
    public void setAggregation(String aggregation) throws AggregationTypeNotAllowedException {
        this.aggregation = aggregation;
        this.validateAggregationType();
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public Date getFrom() {
        return from;
    }

    /**
     * Sets from.
     *
     * @param from - is the initial date for filtering sensor logs
     */
    public void setFrom(Date from) {
        this.from = from;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public Date getTo() {
        return to;
    }

    /**
     * Sets to.
     *
     * @param to - is the final date for filtering sensor logs
     */
    public void setTo(Date to) {
        this.to = to;
    }


}
