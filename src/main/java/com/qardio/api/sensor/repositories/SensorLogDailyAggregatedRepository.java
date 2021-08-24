package com.qardio.api.sensor.repositories;

import com.qardio.api.sensor.models.SensorLogDailyAggregated;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface SensorLogDailyAggregatedRepository extends MongoRepository<SensorLogDailyAggregated, String> {

    List<SensorLogDailyAggregated> findByWhen(Date date);

}
