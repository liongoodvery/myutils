#!/bin/bash
mvn assembly:assembly -Dmaven.test.skip=true &&
cp target/myutils-1.0-SNAPSHOT.jar /home/lion/bin/myutils.jar
