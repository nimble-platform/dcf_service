# dcf_service

> ** DataChannel Dcfs - CustomProducerManager



<a name="getting-started"></a>
## Getting Started
This example code wants to help end users to write messages also over Ibm Kafka in Nimble.

### - start
clone the project in https://github.com/nimble-platform/dcf_service

if kafka works in SSL set environment variable
dcfs_ssl_login=login
dcfs_ssl_password=changeit (usually by using APIKEY)


- customize your properties files
Dcfs 
	dcfs.custom.CustomProducer[0..n] - activate custom producer class
	dcfs.topic.producer.propertiesfile - define if you are working with Ibm Kafka (Ibm Producer) or Generic Kafka (DcfsProducer)
	
DcfsProducer - configure the client to work with generic Kafka
IbmProducer - configure the client to work with Ibm Kafka

other property files are for each example producer

- create your own producer

- compile
run maven on dcf_service/dcf_service_producer/pom.xml


 ---
The project leading to this application has received funding from the European Union Horizon 2020 research and innovation programme under grant agreement No 723810.