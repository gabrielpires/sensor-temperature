package com.qardio.api.sensor.models;

import java.util.Date;

public record SensorLogAggregated(
        Date when,
        Float averageTemperature,
        Integer total
) {
}
