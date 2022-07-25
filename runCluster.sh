#!/usr/bin/env bash

java -Dspring.profiles.active=$1 -jar hotel-server/application/target/application-1.0-SNAPSHOT.jar