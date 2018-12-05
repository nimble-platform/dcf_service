#!/bin/bash
echo "Run DCFS MirrorMaker for IBM with DCFS_IBM_KAFKA_API_KEY "
echo $DCFS_IBM_KAFKA_API_KEY
sed -i -e "s/changeme/${DCFS_IBM_KAFKA_API_KEY}/g" mirror_consumerIBM.properties
##kafka-mirror-maker --consumer.config /etc/mirrorMaker/mirror_consumerIBM.properties --producer.config /etc/mirrorMaker/mirror_producer_dcfs.properties --whitelist '${DCFS_TOPIC_TO_MIRROR}' --num.streams 1
