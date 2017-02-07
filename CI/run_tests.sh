#!/bin/bash

export JAVA_HOME=$(/usr/libexec/java_home)
export PATH=/opt/apache-maven-3.3.9/bin:$JAVA_HOME/bin:$PATH

mvn clean install test -DsuiteXmlFile=hdcalculationstestcases10.xml
