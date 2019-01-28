# dcf_service

> ** DataChannel Filtering Service rel 0.5 - 5.12.2018 **



<a name="getting-started"></a>
## Getting Started
This service want to be an add-on in Nimble in order to allow the usage of Data-Channel build over Kafka and to filter topic's data to offer to consumers many views on the same messages set or subset of data.
This service allow data producers to grant different level of access control over messages - in next release it will be integrated with Nimble Identity service - they are going to publish into Data-Channel by filtering data per row with WHERE conditions - similarly to sql - and to choice list of  columns to show.

The heart of the service is Kafka which is accessable through KSQL by using exposed rest service  in Tomcat. The access to the Kafka (through KSQL) is managed through a set of rules and filter instructions written in an internal instance of database accessed in read only. In Nimble we will have kafka for producers and for normal consumers provided by platform over bluemix; if use case need to filter published messages, to create other view of or subset of them for special consumers, this service is usefull.

Channel, users, group and filter have to be configured in initial sql in internal mysql.

### - start
clone the project in https://github.com/nimble-platform/dcf_service

customize your .env file
in order to connect to Ibm Bluemix Kafka service you need to configure
MESSAGE_HUB_CREDENTIALS={   "instance_id": "changeme",   "mqlight_lookup_url": "changeme",   "api_key": "changeme",   "kafka_admin_url": "changeme",   "kafka_rest_url": "changeme",   "kafka_brokers_sasl": [     "changeme:9093",     "changeme:9093"],   "user": "changeme",   "password": "changeme" }

if kafka works in SSL set environment variable
dcfs_ssl_login=login
dcfs_ssl_password=changeit (usually by using APIKEY)

./runDcfsNimble.sh &
   in a docker-compose enabled sys op.

### - stop
docker-compose down

### - after start

- check healt
http://localhost:28080/dcf-service/

- restart dcfs (create stream, re-create topics)
http://localhost:28080/dcf-service/admin/restartSystem/nimble/nimble

- login and streams name
http://localhost:28080/dcf-service/consumer/loginConsumer/CS/pwd
http://localhost:28080/dcf-service/consumer/listAvaiableDataChannel/CS/pwd/1
http://localhost:28080/dcf-service/consumer/dataChannelMetadata/CS/pwd/1/IT_WHIRPOOL_STREAMS_DC_PRODUCTIONDATA

- produce data
directly in ibm kafka 

- filter data (the dcf service offers filter only over data produced after his run; the oldest messages are ignored)
http://localhost:28080/dcf-service/consumer/filterChannel/CS/pwd/1/IT_WHIRPOOL_STREAMS_DC_PRODUCTIONDATA/serialnumber='151710001772'
http://localhost:28080/dcf-service/consumer/filterChannel/CS/pwd/1/IT_WHIRPOOL_STREAMS_DC_PRODUCTDATA/serialnumber='371149701568'
http://localhost:28080/dcf-service/consumer/filterChannel/CS/pwd/1/IT_WHIRPOOL_STREAMS_DC_INTERVENTDATA/serialnumber='371149701568'

- "no grant" example
http://localhost:28080/dcf-service/consumer/filterChannel/FSTF/pwd/1/IT_WHIRPOOL_STREAMS_DC_PRODUCTIONDATA/serialnumber='151710001772'




 ---
The project leading to this application has received funding from the European Union Horizon 2020 research and innovation programme under grant agreement No 723810.