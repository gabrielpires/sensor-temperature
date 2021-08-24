# Sensor Logger
This is an assignment project created by Gabriel Pires for Qardio.


## Purpose of this exercise
In this exercise I was asked to create a simple REST API to save a temperature sensor measures.
- an endpoint to save temperature data
- an endpoint to retrieve the aggregated temperature data (hourly, daily). 
  - Performance is
  critical on this endpoint, so it should return the data as fast as possible.

## Development Process

### Approach for development
* Definition of the Architecture (explained below)
* First to have a working version, saving and a simple aggregation generation
* Once the working version was in place I refactor to generate the aggregations while saving the new logs
* Improvement of aggregation generation to touch only the records based on the date  
* Containerization of the java application to build and execute in a single flow 

### Assumptions
* For the aggregation the temperature would be an average
* There's no identification of which sensor is sending
* Saving the logs of the sensor accepts duplicated data
* To support single saving and bulk saving I took the approach of always expecting a list, this could also be solved with an additional endpoint special for bulk


### Flow Diagram
#### Delivered Flow + Production Suggestion
![Demo](https://gabrielpires.com.br/images/qardio_challenge.png)
[Full size at url: https://gabrielpires.com.br/images/qardio_challenge.png](https://gabrielpires.com.br/images/qardio_challenge.png)

### Stack
* Java 16
* MongoDB

## Dependencies
* springboot 2.5.4
    * spring-boot-starter-data-mongodb
    * spring-boot-starter-web 
    * spring-boot-starter-test</artifactId>

## Requirements to run
In order to run this app:
* Docker Engine 20.10.x
* Compose 1.29.0

## To run the application
Download the application content
```
$ cd <APPLLICATION_PATH>
```
Environment Variables file 
```
$ cp .env.dist .env
```
Building the container
```
$ docker-compose build
```
Running the container
```
$ docker-compose up
```

You will see a **similar** output

```
[+] Running 2/2
 ⠿ Container sensor-mongo  Started                                                                                                0.8s
 ⠿ Container sensor-api    Started                                                                                                1.5s
Attaching to sensor-api, sensor-mongo
sensor-mongo  | {"t":{"$date":"2021-08-24T16:46:56.683+00:00"},"s":"I",  "c":"STORAGE",  "id":22430,   "ctx":"initandlisten","msg":"WiredTiger message","attr":{"message":"[1629823616:683874][1:0x7fd96c293c80], txn-recover: [WT_VERB_RECOVERY_PROGRESS] Recovering log 4 through 5"}}
sensor-mongo  | {"t":{"$date":"2021-08-24T16:46:57.798+00:00"},"s":"I",  "c":"STORAGE",  "id":22430,   "ctx":"initandlisten","msg":"WiredTiger message","attr":{"message":"[1629823617:798514][1:0x7fd96c293c80], txn-recover: [WT_VERB_RECOVERY_PROGRESS] Recovering log 5 through 5"}}
sensor-api    |
sensor-api    |   .   ____          _            __ _ _
sensor-api    |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
sensor-api    | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
sensor-api    |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
sensor-api    |   '  |____| .__|_| |_|_| |_\__, | / / / /
sensor-api    |  =========|_|==============|___/=/_/_/_/
sensor-api    |  :: Spring Boot ::                (v2.5.4)
sensor-api    |
```

The application will be running awaiting to REST calls at http://localhost:8513

MongoDB will be also exposed at 27017

### To test the application
Along with the project, I'm sharing an INSOMNIA collection at 
```
<APPLICATION_FOLDER>/etc/insomnia
```
In case INSOMNIA doesn't fit for tests, here is the cURL requests

#### SAVE SENSOR LOG
```
curl --request POST \
  --url http://localhost:8513/log \
  --header 'Content-Type: application/json' \
  --data '[
	{
		"temperature": 2.5,
		"when": "2021-08-24T19:19:15+02:00"
	},
		{
		"temperature": 30.5,
		"when": "2021-08-27T18:00:00.000"
	}
	,
	{
		"temperature": 50.5,
		"when": "2021-08-20T18:16:00.000"
	}
	

]
'
```
RESPONSE:
```
{
  "logsSaves": 3,
  "aggregationGenerated": true,
  "logsToSave": 3
}
```

#### LIST Logs Aggregate DAILY
```
curl --request GET \
  --url http://localhost:8513/log \
  --header 'Content-Type: application/json' \
  --data '{
	"aggregationType":"daily"
}'
```

#### LIST Logs Aggregate HOURLY
```
curl --request GET \
  --url http://localhost:8513/log \
  --header 'Content-Type: application/json' \
  --data '{
	"aggregationType":"hourly"
}'
```

The list endpoint also supports date filtering
```
curl --request GET \
  --url http://localhost:8513/log \
  --header 'Content-Type: application/json' \
  --data '{
	"aggregationType":"hourly",
	"from":"2021-08-20T18:00:00.000+00:00",
	"to":"2021-08-25T18:00:00.000+00:00"
}'
```

RESPONSE:
```
[
  {
    "id": "2021-08-20T18:00:00.000",
    "when": "2021-08-20T18:00:00.000+00:00",
    "averageTemperature": 75.5,
    "totalRecords": 2
  },
  {
    "id": "2021-08-23T18:00:00.000",
    "when": "2021-08-23T18:00:00.000+00:00",
    "averageTemperature": 30.5,
    "totalRecords": 2
  },
  {
    "id": "2021-08-24T15:00:00.000",
    "when": "2021-08-24T15:00:00.000+00:00",
    "averageTemperature": 79.57692,
    "totalRecords": 13
  },
  {
    "id": "2021-08-24T16:00:00.000",
    "when": "2021-08-24T16:00:00.000+00:00",
    "averageTemperature": 27.0,
    "totalRecords": 4
  }
]
```

### Environment Variables
At the root folder I added the `.env.dist`. This file is already configured be used by the docker file.
```
MONGODB_AUTH_DATABASE=admin
MONGODB_USER=root
MONGODB_PASS=123456
MONGODB_DATABASE=sensor_log
MONGODB_PORT=27017
MONGODB_HOST=sensor-mongo
```

## Test
### Execution
If don't have MongoDB locally, just keep the docker running before executing the tests

**Important note: To execute the tests, export the environment variables**
```
MONGODB_AUTH_DATABASE=admin MONGODB_USER=root MONGODB_PASS=123456 MONGODB_DATABASE=sensor_log MONGODB_PORT=27017 MONGODB_HOST=localhost
```
To run the tests ON UNIX BASED
```
./mvnw test
```
To run the tests ON WINDOWS
```
mvnw.cmd test
```

### Coverage
```

```

### Other Documentations
For this project a javadoc was generated. You can find it at
```
<APPLICATION_ROOT>/docs
```

## Notes for Production
### Improvements
* Identifying which sensor is sending 
* Generate the aggregations in async way (Message Queue or separeted thread)


