package com.qardio.temperature.controllers;

import com.qardio.temperature.models.SensorLog;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
class SensorController {

    @GetMapping("/temperature/{aggregateType}")
    List<SensorLog> all(@PathVariable String aggregateType){

        List<SensorLog> result = new ArrayList<>();

        for(int i = 0; i < 10; i++){

            SensorLog record = new SensorLog( "id", (float) i, (new Date()));

            result.add(record);
        }

        return result;
    }

    @PostMapping("/temperature")
    List<SensorLog> save(@RequestBody List<SensorLog> list){

        return list;
    }

}
