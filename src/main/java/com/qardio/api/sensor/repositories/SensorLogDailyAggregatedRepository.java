package com.qardio.api.sensor.repositories;

import com.qardio.api.sensor.models.SensorLog;
import com.qardio.api.sensor.models.SensorLogAggregated;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.Date;

public interface SensorLogDailyAggregatedRepository extends MongoRepository<SensorLogAggregated, String> {

}
