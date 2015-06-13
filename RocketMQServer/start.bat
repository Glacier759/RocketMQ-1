@echo off
echo starting
echo starting mqnamesrv
start/b bin/mqnamesrv.exe  >D:\logs\mqnamesrv.log
echo end mqnamesrv----------------

ping 127.0.0.1 - n 1 - w 10000 > nul

echo starting mqbroker
start/b bin/mqbroker.exe -n "127.0.0.1:9876" >D:\logs\mqbroker.log
echo end mqbroker------------------

ping 127.0.0.1 - n 1 - w 10000 > nul

echo end