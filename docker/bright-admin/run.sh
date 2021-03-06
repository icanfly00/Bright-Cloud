#!/bin/bash
echo 'begin docker'
# 查看正在运行的容器
echo 'view container'
docker ps
#停止容器
echo 'stop container'
docker stop bright-admin
#删除容器
echo 'delete container'
docker rm -f bright-admin
#docker images
#删除镜像
echo 'delete images'
docker rmi -f bright-admin
#创建镜像
echo 'create images'
docker build -t bright-admin .
#运行容器
echo 'run container'
docker run -it -d --name bright-admin -p8006:8006 -v /home/output:/output bright-admin
echo 'end docker'
