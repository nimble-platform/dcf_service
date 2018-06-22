#in future
#/home/musumeci/kafka/confluent-4.1.0/bin/confluent start

# Clean old data
ps -ef | grep 'conflu' | grep -v grep | awk '{print $2}' | xargs -r kill -9
rm -R /tmp/*kafka*
rm -R /tmp/zook*
rm /home/musumeci/.ksql-history
rm -R /home/musumeci/kafka/ksql_logs/*
rm -R /home/musumeci/kafka/confluent-4.1.0/logs/*


# Start ZooKeeper
../confluent-4.1.0/bin/zookeeper-server-start ../confluent-4.1.0/etc/kafka/zookeeper.properties &
# Start Kafka
../confluent-4.1.0/bin/kafka-server-start ../confluent-4.1.0/etc/kafka/server.properties &
# Start Kafka-Ksql
../confluent-4.1.0/bin/ksql-server-start ../confluent-4.1.0/etc/ksql/ksql-server.properties &

# Start Schema Registry
../confluent-4.1.0/bin/schema-registry-start ../confluent-4.1.0/etc/schema-registry/schema-registry.properties &
# Start Kafka-rest
../confluent-4.1.0/bin/kafka-rest-start ../confluent-4.1.0/etc/kafka-rest/kafka-rest.properties &
