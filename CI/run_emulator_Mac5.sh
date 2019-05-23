#!/bin/bash

# $1 - test file name

echo "Incoming parameters:"
echo $1
echo "------------------"

export JAVA_HOME=$(/usr/libexec/java_home)
export ANDROID_HOME=/Users/aqc/Library/android/sdk

export PATH=/usr/local/Cellar/maven/3.6.1/bin:$JAVA_HOME/bin:$PATH


nohup $ANDROID_HOME/emulator/emulator -avd myphone -no-snapshot > /dev/null 2>&1 & $ANDROID_HOME/platform-tools/adb wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do sleep 1; done; input keyevent 82' 
