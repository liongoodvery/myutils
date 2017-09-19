#!/bin/bash
mvn assembly:assembly -Dmaven.test.skip=true &&
cp target/org.lion-1.0-SNAPSHOT.jar /home/lion/bin/myutils.jar
