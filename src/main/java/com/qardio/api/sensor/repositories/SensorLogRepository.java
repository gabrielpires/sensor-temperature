package com.qardio.api.sensor.repositories;

import com.qardio.api.sensor.models.SensorLog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The interface Sensor log repository.
 */
public interface SensorLogRepository extends MongoRepository<SensorLog, String> {

}
