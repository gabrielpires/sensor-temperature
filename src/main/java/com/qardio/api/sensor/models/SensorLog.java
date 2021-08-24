package com.qardio.api.sensor.models;

import java.util.Date;

public record SensorLog(
        String id,
        Float temperature,
        Date when
) {

    public SensorLog() {
        this(null, null, null);
    }
}
