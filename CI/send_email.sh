#!/bin/bash

#
# $1 - recipient
# $2 - subject
# $3 - path to attachment
# $4 - attachment name
#

echo Incoming parameters:
echo $1
echo $2
echo $3
echo $4

uuencode "$3" $4 | mail -s "$2" $1
