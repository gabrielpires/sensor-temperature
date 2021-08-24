package com.qardio.api.sensor.repositories;

import com.qardio.api.sensor.models.SensorLogDailyAggregated;
import com.qardio.api.sensor.models.SensorLogHourlyAggregated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * The type Sensor log aggregated repository.
 */
@Component("sensorLogAggregateRepository")
public class SensorLogAggregatedRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * Instantiates a new Sensor log aggregated repository.
     *
     * @param mongoTemplate the mongo template
     */
    @Autowired
    public SensorLogAggregatedRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * List sensor log aggregated list.
     *
     * @return the list
     * @see SensorLogDailyAggregated
     */
    public List<SensorLogDailyAggregated> listSensorLogDailyAggregated() {
        return this.listSensorLogDailyAggregated(null, null);
    }

    /**
     * List sensor log aggregated list.
     *
     * @param from the from
     * @param to   the to
     * @return the list
     * @see SensorLogDailyAggregated
     */
    public List<SensorLogDailyAggregated> listSensorLogDailyAggregated(Date from, Date to) {


        return mongoTemplate.aggregate(
                this.getAggregation(false, from, to),
                "sensorLog",
                SensorLogDailyAggregated.class
        ).getMappedResults();
    }

    /**
     * List sensor log aggregated list.
     *
     * @return the list
     * @see SensorLogHourlyAggregated
     */
    public List<SensorLogHourlyAggregated> listSensorLogHourlyAggregated() {
        return this.listSensorLogHourlyAggregated(null, null);
    }

    /**
     * List sensor log aggregated list.
     *
     * @param from the from
     * @param to   the to
     * @return the list
     * @see SensorLogHourlyAggregated
     */
    public List<SensorLogHourlyAggregated> listSensorLogHourlyAggregated(
            Date from,
            Date to
    ) {

        return mongoTemplate.aggregate(
                this.getAggregation(true, from, to),
                "sensorLog",
                SensorLogHourlyAggregated.class
        ).getMappedResults();
    }

    /**
     * Get aggregation aggregation.
     *
     * @param hourly the hourly
     * @param from   the from
     * @param to     the to
     * @return the aggregation
     */
    private Aggregation getAggregation(boolean hourly, Date from, Date to) {

        ProjectionOperation projectionOperation = this.getProjectionOperation(hourly);
        GroupOperation groupOperation = this.getGroupOperation();
        SortOperation sortOperation = this.getSortOperation();

        //we cannot use an match without criteria
        //for that reason we create a aggregation without match
        if (from == null && to == null) {
            return newAggregation(
                    projectionOperation,
                    groupOperation,
                    sortOperation
            );
        }

        return newAggregation(
                this.getMatchOperation(from, to),
                projectionOperation,
                groupOperation,
                sortOperation
        );
    }

    /**
     * Get match operation match operation.
     *
     * @param from the from
     * @param to   the to
     * @return the match operation
     */
    private MatchOperation getMatchOperation(Date from, Date to) {

        Criteria matchCriteria = new Criteria("when");
        if (from != null) {
            matchCriteria.gte(from);
        }

        if (to != null) {
            matchCriteria.lte(to);
        }

        return Aggregation.match(matchCriteria);
    }

    /**
     * Get projection operation projection operation.
     *
     * @param hourly the hourly
     * @return the projection operation
     */
    private ProjectionOperation getProjectionOperation(Boolean hourly) {

        return Aggregation.project()
                .andExpression(this.getDateExpressionForQuery(hourly)).as("aggregatedDate")
                .and("temperature").as("temperature");
    }

    /**
     * Get group operation group operation.
     *
     * @return the group operation
     */
    private GroupOperation getGroupOperation() {
        return group(fields().and("aggregatedDate"))
                .first(ConvertOperators.valueOf("aggregatedDate").convertToDate()).as("when")
                .avg("temperature").as("averageTemperature")
                .count().as("totalRecords");

    }

    /**
     * Get sort operation sort operation.
     *
     * @return the sort operation
     */
    private SortOperation getSortOperation() {
        return Aggregation.sort(Sort.Direction.ASC, "when");
    }

    /**
     * Get date expression for query string.
     *
     * @param hourly the hourly
     * @return the string
     */
    private String getDateExpressionForQuery(boolean hourly) {

        String dateFormat = "%Y-%m-%dT00:00:00.000";

        if (hourly) {
            dateFormat = "%Y-%m-%dT%H:00:00.000";
        }

        return String.format("dateToString('%s', when)", dateFormat);
    }
}
