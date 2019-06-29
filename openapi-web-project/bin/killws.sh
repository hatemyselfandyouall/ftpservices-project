#!/bin/bash

cd `dirname $0`/..
BASE=`pwd`

PROJECT_PATH=$BASE

. "$BASE/bin/setenv.sh"

# top httpd
echo "isrun =$ISRUN"
bash $BASE/bin/httpd.sh stop

if [ "$ISRUN" == "1" ]; then
	echo "Stoping ${PROJECT}..."
	$JAVA_HOME/bin/java -jar $libPath/jetty-start-*.jar -DSTOP.PORT=${STOPPORT} -DSTOP.KEY=${PROJECT} --stop
fi
