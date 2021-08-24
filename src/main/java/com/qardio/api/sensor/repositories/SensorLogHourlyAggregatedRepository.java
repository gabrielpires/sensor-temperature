package com.qardio.api.sensor.repositories;

import com.qardio.api.sensor.models.SensorLogHourlyAggregated;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * The interface Sensor log hourly aggregated repository.
 */
public interface SensorLogHourlyAggregatedRepository extends MongoRepository<SensorLogHourlyAggregated, String> {
    /**
     * Find by when list.
     *
     * @param date the date
     * @return the list
     */
    List<SensorLogHourlyAggregated> findByWhen(Date date);
}
