#!/bin/bash

BASE=$PROJECT_PATH

if [ -z "$PROJECT_PATH" ] ; then
	cd `dirname $0`/..
	BASE=`pwd`
fi

if [ `cat /proc/version|grep Ubuntu|wc -l` == "1" ] ; then

export TZ='Asia/Shanghai';

else

export LANG=zh_CN
export LC_ALL=zh_CN.UTF-8

fi

export JAVA_HOME=/usr/local/java

export SERVICE_NAME=${BASE##*/}

BASEROOT=""
if [ -d $BASE/conf ];then
	confPath=$BASE/conf
	BASEROOT="$BASE/../"
else
	confPath=$BASE/target/conf
fi

STDOUTFILE="${BASEROOT}logs/$SERVICE_NAME/stdout.log"
mkdir -p ${BASEROOT}logs/$SERVICE_NAME

CLASSPATH=`echo $JAVA_HOME/lib/*.jar | tr ' ' ':'`
if [ -d $BASE/lib ];then
	CLASSPATH=$CLASSPATH:`echo $BASE/lib/*.jar | tr ' ' ':'`
	libPath=$BASE/lib
else
	rm -rf $BASE/target/*-javadoc.jar
	rm -rf $BASE/target/*-sources.jar
	CLASSPATH=$CLASSPATH:`echo $BASE/target/libs/*.jar | tr ' ' ':'`
	CLASSPATH=$CLASSPATH:`echo $BASE/target/*.jar | tr ' ' ':'`
	libPath=$BASE/target/libs
	JettyConf="--jar $BASE/target/*.jar"
fi

BOOTLIB=`echo $BASE/lib/slf4j-*.jar | tr ' ' ':'`:`echo $BASE/lib/log4j-*.jar | tr ' ' ':'`

JettyConf="--classes ${confPath} ${JettyConf} --lib $libPath --config ${confPath}/jetty.conf"

CLASSPATH=$CLASSPATH:$confPath

#debug
file $confPath/http.conf | grep -q CRLF && dos2unix $confPath/http.conf
PORT=$(grep -i "listen" $confPath/httpd.conf |awk 'NR==1'|sed -e 's/\s*//g'|sed -e 's/;//'|cut -dn -f 2)
let "RUNPORT = $PORT + 1"
let "STOPPORT = $PORT + 2"
PROJECT=${SERVICE_NAME}

ISRUN=$(ps ux|grep "/$PROJECT/"|grep java |wc -l)

#echo "$BASE/../binconf/jvm.properties, loading debug config"
if [ -f "$BASE/../binconf/jvm.properties" ]; then
    DEBUGPORT=$(grep -i "^$SERVICE_NAME.debug.port\s*=" $BASE/../binconf/jvm.properties | cut -d= -f 2| sed 's/^ //;s/ $//')
    #echo "DEBUGPORT=$DEBUGPORT"
    if [ -n "$DEBUGPORT" ]; then
        DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_ftpservices,address=$DEBUGPORT,server=y,suspend=n"
    fi
    #echo "DEBUG_OPTS=$DEBUG_OPTS"
fi

#run
#echo "$BASE/../binconf/jvm.properties"
if [ -f "$BASE/../binconf/jvm.properties" ]; then
#    echo true
    MSSIZE=$(grep -i "^jvm.xms\s*=" $BASE/../binconf/jvm.properties | cut -d= -f 2| sed 's/^ //;s/ $//')
    MXSIZE=$(grep -i "^jvm.xmx\s*=" $BASE/../binconf/jvm.properties | cut -d= -f 2| sed 's/^ //;s/ $//')
    #echo "xxh--jvm1-------test-------${SERVICE_NAME} MSSIZE=${MSSIZE}"
elif [ "$SERVICE_NAME" == "cps-web-app" ] ; then
    MSSIZE=3g
    MXSIZE=3g
    #echo "xxh--jvm2-------test-------${SERVICE_NAME} MSSIZE=${MSSIZE}"
else
	MSSIZE=1g
    MXSIZE=1g
     #echo "xxh--jvm3-else-------test-------${SERVICE_NAME} MSSIZE=${MSSIZE}"
fi
#echo "MSSIZE=$MSSIZE MXSIZE=$MXSIZE"

ZKINFO="-Dmonitor.jvm=on -Dmonitor.heartbeat.interval=10"

JAVA_CONF="-XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+AggressiveOpts -XX:+UseFastAccessorMethods -Xms$MSSIZE -Xmx$MXSIZE -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=5 $DEBUG_OPTS -Djava.io.tmpdir=$BASE/tmp/run.log $ZKINFO -Dbase.name=$SERVICE_NAME -Dbase.root=$BASEROOT -Dlog4j.config=$confPath/log4j.properties"
if [ "$SERVICE_NAME" == "cps-web-app" ] ; then
	JAVA_CONF="-Xmn1200M -XX:SurvivorRatio=6 $JAVA_CONF" 
	#echo "xxh--jvm java_conf -test-------${SERVICE_NAME} JAVA_CONF=${JAVA_CONF}"
else
   echo "."
fi