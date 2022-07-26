#!/usr/bin/env bash

declare SERVER=$1
declare HTTP_PORT=$2
declare SEED_NODE_PORT=$3
declare REMOTE_PORT=$4
declare MANAGEMENT_PORT=$5

if [ -z "$SERVER" ]; then
    echo "ERROR: must specify server name on first argument"
    exit -1
fi

if [ -z "$HTTP_PORT" ]; then
    echo "ERROR: must specify port on second argument"
    exit -1
fi

if [ -z "$SEED_NODE_PORT" ]; then
    echo "ERROR: must specify seedNode port on third argument"
    exit -1
fi

if [ -z "$REMOTE_PORT" ]; then
    echo "ERROR: must specify remote port on fourth argument"
    exit -1
fi
if [ -z "$MANAGEMENT_PORT" ]; then
    echo "ERROR: must specify management port on fifth argument"
    exit -1
fi


 if [ ! -f "./mvnw" ];then
    echo "ERROR: ${pwd}/mvnw not exist"
    exit -1
fi

declare SEED_NODE="akka://$SERVER@127.0.0.1:$SEED_NODE_PORT"

chmod +x ./mvnw && ./mvnw -pl $SERVER -am spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=$HTTP_PORT -Dquantex.phoenix.cluster.config.seed-node=$SEED_NODE -Dquantex.phoenix.akka.artery-canonical-port=$REMOTE_PORT -Dquantex.phoenix.akka.management-http-port=$MANAGEMENT_PORT"

echo "done"
