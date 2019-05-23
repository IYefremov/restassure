#!/bin/bash

# $1 - test file name

echo "Incoming parameters:"
echo $1
echo "------------------"

export JAVA_HOME=$(/usr/libexec/java_home)
export ANDROID_HOME=/Users/aqc/Library/android/sdk

export PATH=/usr/local/Cellar/maven/3.6.1/bin:$JAVA_HOME/bin:$PATH


cd $ANDROID_HOME/emulator
./emulator -avd myphone -dns-server 8.8.8.8
