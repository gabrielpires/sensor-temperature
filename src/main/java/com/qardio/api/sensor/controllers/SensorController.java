package com.qardio.api.sensor.controllers;

import com.qardio.api.sensor.models.SensorLog;
import com.qardio.api.sensor.payloads.ListSensorLogs;
import com.qardio.api.sensor.payloads.SaveSensorLog;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
class SensorController {

    @GetMapping("/temperature")
    List<SensorLog> all(@RequestBody ListSensorLogs payload){

        List<SensorLog> result = new ArrayList<>();

        for(int i = 0; i < 10; i++){

            SensorLog record = new SensorLog( "id", (float) i, (new Date()));

            result.add(record);
        }

        return result;
    }

    @PostMapping("/temperature")
    List<SensorLog> save(@RequestBody List<SaveSensorLog> payload){

        return null;
    }

}
