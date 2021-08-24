package com.qardio.api.sensor.repositories;

import com.qardio.api.sensor.models.SensorLogAggregated;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SensorLogHourlyAggregatedRepository  extends MongoRepository<SensorLogAggregated, String> {

}
