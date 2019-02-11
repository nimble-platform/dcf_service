#!/bin/bash

function extractApiKey {
    temp=`echo $json | sed 's/\\\\\//\//g' | sed 's/[{}]//g' | awk -v k="text" '{n=split($0,a,","); for (i=1; i<=n; i++) print a[i]}' | sed 's/\"\:\"/\|/g' | sed 's/[\,]/ /g' | sed 's/\"//g' | grep -w $prop | cut -d":" -f2| sed -e 's/^ *//g' -e 's/ *$//g'`
    echo ${temp##*|}
}

	json=${MESSAGE_HUB_CREDENTIALS}
	
	prop='api_key'
	
	ibm_kafka_apikey=`extractApiKey`

	echo ${ibm_kafka_apikey}
	
	sed "s/ibm_kafka_apikey/${ibm_kafka_apikey}/g" /etc/ksql/ksql-server.properties.template > /etc/ksql/ksql-server.properties
	