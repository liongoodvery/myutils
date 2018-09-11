#!/bin/bash
ROOT_DIR=$PWD
cd $ROOT_DIR/di && \
mvn clean assembly:assembly -Dmaven.test.skip=true && \
cp target/di-1.0-SNAPSHOT.jar /home/lion/bin/myutils.jar && \
cd $ROOT_DIR/tools && \
mvn clean assembly:assembly -Dmaven.test.skip=true && \
cp target/tools-1.0-SNAPSHOT.jar /home/lion/bin/mytools.jar && \
cd $ROOT_DIR

