#!/bin/bash
echo 'begin docker'
# 查看正在运行的容器
echo 'view container'
docker ps
#停止容器
echo 'stop container'
docker stop bright-auth
#删除容器
echo 'delete container'
docker rm -f bright-auth
#docker images
#删除镜像
echo 'delete images'
docker rmi -f bright-auth
#创建镜像
echo 'create images'
docker build -t bright-auth .
#运行容器
echo 'run container'
docker run -it -d --name bright-auth -p8001:8001 -v /home/output:/output bright-auth
echo 'end docker'
