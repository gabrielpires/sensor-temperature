package com.qardio.api.sensor.modules;

import com.mongodb.DuplicateKeyException;
import com.qardio.api.sensor.models.SensorLogDailyAggregated;
import com.qardio.api.sensor.models.SensorLogHourlyAggregated;
import com.qardio.api.sensor.repositories.SensorLogAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogDailyAggregatedRepository;
import com.qardio.api.sensor.repositories.SensorLogHourlyAggregatedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Sensor log aggregator builder.
 */
@Component("sensorLogAggregatorBuilder")
public class SensorLogAggregatorBuilder {

    @Autowired
    private final SensorLogAggregatedRepository sensorLogAggregatedRepository;

    @Autowired
    private final SensorLogDailyAggregatedRepository sensorLogDailyAggregatedRepository;

    @Autowired
    private final SensorLogHourlyAggregatedRepository sensorLogHourlyAggregatedRepository;

    private final List<Date> datesToUpdate;

    private final Calendar calendar;

    /**
     * Instantiates a new Sensor log aggregator builder.
     *
     * @param sensorLogAggregatedRepository
     * @param sensorLogDailyAggregatedRepository
     * @param sensorLogHourlyAggregatedRepository
     */
    public SensorLogAggregatorBuilder(
            SensorLogAggregatedRepository sensorLogAggregatedRepository,
            SensorLogDailyAggregatedRepository sensorLogDailyAggregatedRepository,
            SensorLogHourlyAggregatedRepository sensorLogHourlyAggregatedRepository
    ) {
        this.sensorLogAggregatedRepository = sensorLogAggregatedRepository;
        this.sensorLogDailyAggregatedRepository = sensorLogDailyAggregatedRepository;
        this.sensorLogHourlyAggregatedRepository = sensorLogHourlyAggregatedRepository;
        this.calendar = Calendar.getInstance();
        this.datesToUpdate = new ArrayList<>();
    }

    /**
     * Build.
     * <p>
     * For each date set in the internal list, we will project a aggregated calculation
     * This will perform the projection of DAILY and HOURLY aggregation based on the
     * available dates.
     */
    public void build() {
        datesToUpdate.forEach(this::project);
    }

    /**
     * Project.
     * <p>
     * This function will build from and to dates based on the original date
     * Using the from and to date, we fetch the data aggregated for 2 different types: DAILY and HOURLY
     * After that we project back the data to mongodb
     *
     * @param date the date
     */
    private void project(Date date) {

        Map<String, Date> dailyDates = this.getDailyFromAndTo(date);
        Map<String, Date> hourlyDates = this.getDailyFromAndTo(date);

        List<SensorLogDailyAggregated> dailyAggregationLogs = sensorLogAggregatedRepository.listSensorLogDailyAggregated(
                dailyDates.get("from"),
                dailyDates.get("to")
        );

        for (SensorLogDailyAggregated sensorLogAggregated : dailyAggregationLogs) {
            try {
                sensorLogDailyAggregatedRepository.save(sensorLogAggregated);
            } catch (Exception exception) {
                SensorLogDailyAggregated existingSensorLogDailyAggregated = sensorLogDailyAggregatedRepository.findByWhen(
                        sensorLogAggregated.when
                ).get(0);

                sensorLogAggregated.id = existingSensorLogDailyAggregated.id;
                sensorLogDailyAggregatedRepository.save(sensorLogAggregated);
            }
        }

        List<SensorLogHourlyAggregated> hourlyAggregationLogs = sensorLogAggregatedRepository.listSensorLogHourlyAggregated(
                hourlyDates.get("from"),
                hourlyDates.get("to")
        );

        for (SensorLogHourlyAggregated sensorLogAggregated : hourlyAggregationLogs) {
            try {
                sensorLogHourlyAggregatedRepository.save(sensorLogAggregated);
            } catch (Exception exception) {
                SensorLogHourlyAggregated existingSensorLogDailyAggregated = sensorLogHourlyAggregatedRepository.findByWhen(
                        sensorLogAggregated.when
                ).get(0);

                sensorLogAggregated.id = existingSensorLogDailyAggregated.id;
                sensorLogHourlyAggregatedRepository.save(sensorLogAggregated);
            }
        }


    }

    /**
     * From the base date eg 2021-08-23 13:00:00 we will get a from and to date
     * This range is created expanding de from date to 00:00:00 and the to date to 23:59:59
     * The result will be the Map<String, Date> with keys: from and to
     *
     * @param date the date
     * @return the map
     */
    private Map<String, Date> getDailyFromAndTo(Date date) {

        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date from = new Date(calendar.getTime().getTime());

        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date to = new Date(calendar.getTime().getTime());

        Map<String, Date> result = new HashMap<>();
        result.put("from", from);
        result.put("to", to);

        return result;
    }

    /**
     * From the base date eg 2021-08-23 13:00:00 we will get a from and to date
     * This range is created expanding de from date to XX:00:00 and the to date to XX:59:59
     * The result will be the Map<String, Date> with keys: from and to
     *
     * @param date the date
     * @return the map
     */
    private Map<String, Date> getHourlyFromAndto(Date date) {

        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        Date from = new Date(calendar.getTime().getTime());

        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date to = new Date(calendar.getTime().getTime());

        Map<String, Date> result = new HashMap<>();
        result.put("from", from);
        result.put("to", to);

        return result;
    }

    /**
     * Add.
     *
     * @param date the date
     * @throws ParseException the parse exception
     */
    public void add(Date date) throws ParseException {

        if (this.datesToUpdate.contains(date)) {
            return;
        }

        this.datesToUpdate.add(this.cleanDate(date));

    }

    /**
     * Clean date date.
     *
     * @param date the date
     * @return the date
     * @throws ParseException the parse exception
     */
    private Date cleanDate(Date date) throws ParseException {

        DateFormat cleanDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:00:00");

        return cleanDateFormat.parse(cleanDateFormat.format(date));
    }

}
