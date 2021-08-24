package com.qardio.api.sensor.repositories;

import com.qardio.api.sensor.models.SensorLogDailyAggregated;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * The interface Sensor log daily aggregated repository.
 */
public interface SensorLogDailyAggregatedRepository extends MongoRepository<SensorLogDailyAggregated, String> {

    /**
     * Find by when list.
     *
     * @param date the date
     * @return the list
     */
    List<SensorLogDailyAggregated> findByWhen(Date date);

}
