# dcf-service

> ** DataChannel Filtering Service **

<a name="getting-started"></a>
## Getting Started
Access and filter Iot Data by using the easiness of Sql. Apply filter at runtime whitout having to manage hard to code low level Kafka Stream.

This service is integrated with SSO in Nimble (Identity Service) and allow data producers to add another level of access control over messages they are going to publish into Data-Channel by filtering data per row with WHERE conditions - similarly to Sql - and to choice list of  columns to show.

In this release Channel, users, group and filter have to be configured in internal instance of mysql.

This service is deployed in Ibm Nimble Cluster at 
https://nimble.eu-de.containers.appdomain.cloud/dcf-service/

### - start
clone the project in https://github.com/nimble-platform/dcf_service

customize your .env file in order to connect to Ibm Bluemix Kafka service you need to configure
MESSAGE_HUB_CREDENTIALS={   "instance_id": "changeme",   "mqlight_lookup_url": "changeme",   "api_key": "changeme",   "kafka_admin_url": "changeme",   "kafka_rest_url": "changeme",   "kafka_brokers_sasl": [     "changeme:9093",     "changeme:9093"],   "user": "changeme",   "password": "changeme" }

./runDcfsNimble.sh &
   in a docker-compose enabled sys op.

### - stop
docker-compose down

### - after start

- check healt
http://localhost:8080/dcf-service/

- restart dcfs (recreate stream)
http://localhost:8080/dcf-service/admin/restartSystem/nimble/nimble

- by using Identity Service Nimble - Header has to have HeaderParam "Authorization" from Nimble Identity Service
http://localhost:8080/dcf-service/consumer/filterChannel/IT_WHIRPOOL_STREAMS_DC_PRODUCTIONDATA/serialnumber='151710001772'

a) filtering last json data produced by message service
last messages - 
http://localhost:8080/dcf-service/consumer/filterChannel/DCFS_STREAMS_DC_MESSAGESERVICE/*

b) filtering with a like condition DATA LIKE '%workdone' (consider urlencode rule)
http://localhost:8080/dcf-service/consumer/filterChannel/DCFS_STREAMS_DC_MESSAGESERVICE/DATA%20LIKE%20%27%25workdone%27

c) filtering with an attribute which is also keyword - use `SOURCE`='whi' (consider url encode rule)
http://localhost:8080/dcf-service/consumer/filterChannel/DCFS_STREAMS_DC_MESSAGESERVICE/%60SOURCE%60%3D%27whi%27

d) same filter enabled server side with `SOURCE`='whi'
http://localhost:8080/dcf-service/consumer/filterChannel/FSTF/pwd/0/DCFS_STREAMS_DC_MESSAGESERVICEWHI/*


- login added ACL
http://localhost:8080/dcf-service/consumer/loginConsumer/CS/pwd

- list of avaiable data channel for this user
http://localhost:8080/dcf-service/consumer/listAvaiableDataChannel/CS/pwd/1

- get metadata for a fix data channel for this user
http://localhost:8080/dcf-service/consumer/dataChannelMetadata/CS/pwd/1/IT_WHIRPOOL_STREAMS_DC_PRODUCTIONDATA

- produce data
directly in ibm kafka by subproject https://github.com/nimble-platform/dcf_service/tree/master/dcf_service_producer

- filter data (the dcf service offers filter only over data produced after his run; the oldest messages are ignored)
http://localhost:8080/dcf-service/consumer/filterChannel/CS/pwd/1/IT_WHIRPOOL_STREAMS_DC_PRODUCTIONDATA/serialnumber='151710001772'
http://localhost:8080/dcf-service/consumer/filterChannel/CS/pwd/1/IT_WHIRPOOL_STREAMS_DC_PRODUCTDATA/serialnumber='371149701568'
http://localhost:8080/dcf-service/consumer/filterChannel/CS/pwd/1/IT_WHIRPOOL_STREAMS_DC_INTERVENTDATA/serialnumber='371149701568'

- try to access data with "no grant"
http://localhost:8080/dcf-service/consumer/filterChannel/FSTF/pwd/1/IT_WHIRPOOL_STREAMS_DC_PRODUCTIONDATA/serialnumber='151710001772'

- for End Users developer there is also a dev server
An Holonix istance in http://149.202.69.235:8080/dcf-service/



 ---
The project leading to this application has received funding from the European Union Horizon 2020 research and innovation programme under grant agreement No 723810.