#!/bin/bash
echo 'begin docker'
# 查看正在运行的容器
echo 'view container'
docker ps
#停止容器
echo 'stop container'
docker stop bright-server-generator
#删除容器
echo 'delete container'
docker rm -f bright-server-generator
#docker images
#删除镜像
echo 'delete images'
docker rmi -f bright-server-generator
#创建镜像
echo 'create images'
docker build -t bright-server-generator .
#运行容器
echo 'run container'
docker run -it -d --name bright-server-generator -p8005:8005 -v /home/output:/output bright-server-generator
echo 'end docker'
