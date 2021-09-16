#!/bin/bash

/usr/libexec/java_home -V
export JAVA_HOME=`/usr/libexec/java_home -v  13.0.2`

export PATH=$JAVA_HOME/bin:$PATH
echo 'export PATH=$JAVA_HOME/bin:$PATH'


echo JAVA_HOME=$JAVA_HOME

mvn clean package
java -jar flake-client/target/flake-client-1.0-SNAPSHOT.jar