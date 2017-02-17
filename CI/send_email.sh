#!/bin/bash

#
# $1 - recipient
# $3 - subject
# $2 - path to attachment
#

echo Incoming parameters:
echo $1
echo $2
echo $3

mail -a "$3" -s "$2" $1 < /dev/null
