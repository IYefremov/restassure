#!/bin/bash

export PATH=/opt/apache-maven-3.3.9/bin:$PATH

mvn clean install test -DsuiteXmlFile=hdcalculationstestcases10.xml
