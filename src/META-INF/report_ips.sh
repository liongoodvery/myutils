#!/bin/zsh
source ~/bin/commons.sh
log=`get_log report_ips`
java -cp /home/lion/IdeaProjects/myutils/out/production/myutils org.lion.utils.PingCheck > $log
