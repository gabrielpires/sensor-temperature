package com.qardio.api.sensor.payloads.exceptions;

public class AggregationTypeNotAllowedException extends Exception {

    public AggregationTypeNotAllowedException(String invalidAggregationType) {
        super(
                String.format(
                        "%s is a invalid type of aggregation for listing logs. Use HOURLY or DAILY instead.",
                        invalidAggregationType
                )
        );
    }
}
