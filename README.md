# dcf_service

> ** DataChannel Filtering Service **



<a name="getting-started"></a>
## Getting Started
This service wanna be an add-on in order to use Data-Channel build over Kafka and filter topic's data to serve to consumers many views on the same set or subset of message data.
This allow producer's data to grant different way to read on a topic, control access to them, filter data per row with where conditions - similarly to sql - and per columns.
The heart of the service is Kafka which is accessed by using Ksql server respecting rules and filter instructions written in a database.

- download and install

Zookeper https://github.com/apache/zookeeper from 0.11.0 released in on 28 Jun 2017 or later
Kafka https://github.com/apache/kafka  from 0.11.0 released in on 28 Jun 2017 or  later
Ksql (from https://github.com/confluentinc/ksql) from 4.1 on 17 Apr 2018 or later

edit zookeeper.properties
edit kafka/server.properties
edit ksql/ksql-server.properties

look at ../etc_config sample files

Start ZooKeeper
../bin/zookeeper-server-start ../etc/kafka/zookeeper.properties &
Start Kafka
../bin/kafka-server-start ../etc/kafka/server.properties &
Start Kafka-Ksql - 
../bin/ksql-server-start ../etc/ksql/ksql-server.properties &

Not mandatory but maybe usefull for other Nimble's layer.

 Start Schema Registry (only for Avro messages - not supported in this service release)
../bin/schema-registry-start ../etc/schema-registry/schema-registry.properties &
 Start Kafka-rest (usefull but not necessary)
../bin/kafka-rest-start ../etc/kafka-rest/kafka-rest.properties &


In other way you can download opensource Confluent distribution starting from 4.1.0 version or 
https://www.confluent.io/download/
and use 
confluent start 
script

service start mysql
mysql -p -u [user] [database] < ../db/holDatachannelfilteringservicedb.sql.sql

(configure data db for your needs - see ../doc/FilterScenarios.txt)

edit persistence.xml 
edit Dcfs.properties

../apache-tomcat-8.5.31/bin/startup.sh

deploy dcf_service.war

read db/Read.me to understand the Demo scenario

launch admin webservices in this order

http://localhost:28080/dcfs/admin/restartSystem/nimble/nimble
in order to create topics and ApacheKafkaStreams

http://localhost:28080/dcfs/admin/startCustomInitializer/nimble/nimble
in order to send to kafka broker messages (in this istance only delimited messages are supported; json un future releases)

to test login (user credentials are set in DB)
http://localhost:28080/dcfs/consumer/loginConsumer/CS/pwd/1

to filter a dataChannel (by using Apache Stream from ksql)
http://localhost:28080/dcfs/consumer/filterChannel/CS/pwd/1/IT_NIMBLE_DCFSDEMO_STREAMS_PRODUCTDATA/serialnumber='151638000903'


Look at ../doc/webservices.txt in order to have some other example of Rest Calls.


 ---
The project leading to this application has received funding from the European Union’s Horizon 2020 research and innovation programme under grant agreement No 723810.