#!/usr/bin/env bash

declare SERVER=$1
declare HTTP_PORT=$2

if [ -z "$SERVER" ]; then
    echo "ERROR: must specify server name on first argument"
    exit -1
fi

if [ -z "$HTTP_PORT" ]; then
    echo "ERROR: must specify port on second argument"
    exit -1
fi

 if [ ! -f "./mvnw" ];then
    echo "ERROR: ${pwd}/mvnw not exist"
    exit -1
fi

chmod +x ./mvnw && ./mvnw -pl $SERVER -am spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=$HTTP_PORT"

echo "done"
