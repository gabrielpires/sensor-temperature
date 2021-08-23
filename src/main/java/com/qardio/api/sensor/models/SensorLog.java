package com.qardio.api.sensor.models;

import java.util.Date;
import java.util.UUID;

public record SensorLog(
        String id,
        Float temperature,
        Date when
) {

    public SensorLog(Float temperature, Date when){
        this(UUID.randomUUID().toString(), temperature, when);
    }

    public SensorLog(Integer temperature, Date when){
        this(UUID.randomUUID().toString(), temperature.floatValue(), when);
    }

    public String getId(){
        if(this.id != null){
            return this.id;
        }

        return this.id = UUID.randomUUID().toString();
    }

}
