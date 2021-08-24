package com.qardio.api.sensor.repositories;

import com.qardio.api.sensor.models.SensorLogHourlyAggregated;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface SensorLogHourlyAggregatedRepository extends MongoRepository<SensorLogHourlyAggregated, String> {
    List<SensorLogHourlyAggregated> findByWhen(Date date);
}
