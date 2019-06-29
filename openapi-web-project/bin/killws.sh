#!/bin/bash

cd `dirname $0`/..
BASE=`pwd`

PROJECT_PATH=$BASE

. "$BASE/bin/setenv.sh"

# top httpd
echo "xxh-1-isrun =$ISRUN"
bash $BASE/bin/httpd.sh stop
echo "xxh-2-isrun =$ISRUN"
if [ "$ISRUN" == "1" ]; then
	echo "Stoping ${PROJECT}..."
	$JAVA_HOME/bin/java -jar $libPath/jetty-start-*.jar -DSTOP.PORT=${STOPPORT} -DSTOP.KEY=${PROJECT} --stop
fi
