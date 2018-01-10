# $1 - test file name

#echo "Incoming parameters:"
#echo $1
#echo "------------------"

#export JAVA_HOME=$(/usr/libexec/java_home)
#export ANDROID_HOME=/usr/local/Cellar/android-sdk/24.4.1_1

#export PATH=/opt/apache-maven-3.3.9/bin:$JAVA_HOME/bin:$PATH


mvn clean install test -DsuiteXmlFile=$1
