#!/bin/bash
echo 'begin docker'
# 查看正在运行的容器
echo 'view container'
docker ps
#停止容器
echo 'stop container'
docker stop bright-gateway
#删除容器
echo 'delete container'
docker rm -f bright-gateway
#docker images
#删除镜像
echo 'delete images'
docker rmi -f bright-gateway
#创建镜像
echo 'create images'
docker build -t bright-gateway .
#运行容器
echo 'run container'
docker run -it -d --name bright-gateway -p8002:8002 -v /home/output:/output bright-gateway
echo 'end docker'
