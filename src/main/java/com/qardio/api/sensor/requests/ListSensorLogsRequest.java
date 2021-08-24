package com.qardio.api.sensor.requests;

import com.qardio.api.sensor.enums.AggregationType;
import com.qardio.api.sensor.requests.exceptions.AggregationTypeNotAllowedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * SaveSensorLogRequest is a class to deserialization payload for GET /log
 */
public class ListSensorLogsRequest {

    /**
     * List of allowed aggregation types
     */
    private final ArrayList<AggregationType> allowedAggregations = new ArrayList<>(2) {{
        add(AggregationType.DAILY);
        add(AggregationType.HOURLY);
    }};

    /**
     * Aggregation is the type of aggregation for the returned list
     * The value can be DAILY or HOURLY
     */
    private AggregationType aggregationType;

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
    public ListSensorLogsRequest() {
    }

    /**
     * Instantiates a new List sensor logs.
     *
     * @param aggregationType the aggregation
     * @param from            the from
     * @param to              the to
     * @throws AggregationTypeNotAllowedException the aggregation type not allowed exception
     */
    public ListSensorLogsRequest(String aggregationType, Date from, Date to) throws AggregationTypeNotAllowedException {

        if (aggregationType == null) {
            //setting the first allowed as the default (CURRENTLY DAILY)
            aggregationType = AggregationType.DAILY.toString();
        }

        this.setAggregationType(aggregationType);
        this.from = from;
        this.to = to;
    }

    /**
     * Gets aggregation.
     *
     * @return the aggregation
     */
    public AggregationType getAggregationType() {
        if (this.aggregationType == null) {
            this.aggregationType = AggregationType.DAILY;
        }
        return aggregationType;
    }

    /**
     * Sets aggregation.
     *
     * @param aggregationType the aggregation
     * @throws AggregationTypeNotAllowedException the aggregation type not allowed exception
     */
    public void setAggregationType(AggregationType aggregationType) throws AggregationTypeNotAllowedException {
        this.aggregationType = aggregationType;
    }

    /**
     * Sets aggregation.
     *
     * @param aggregationType the aggregation
     * @throws AggregationTypeNotAllowedException the aggregation type not allowed exception
     */
    public void setAggregationType(String aggregationType) throws AggregationTypeNotAllowedException {
        try {
            this.aggregationType = AggregationType.valueOf(aggregationType.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new AggregationTypeNotAllowedException(aggregationType);
        }
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
