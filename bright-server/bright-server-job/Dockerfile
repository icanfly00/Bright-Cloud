FROM openjdk:8u212-jre
MAINTAINER JacksonTu 674717739@qq.com

COPY ./target/bright-server-job-1.0.0.RELEASE.jar /bright/bright-server-job-1.0.0.RELEASE.jar
ADD agent/ /agent

ENTRYPOINT ["java", "-javaagent:/agent/skywalking-agent.jar", "-Dskywalking.agent.service_name=bright-server-job", "-Dskywalking.collector.backend_service=skywalkingIp:11800", "-jar", "/bright/bright-server-job-1.0.0.RELEASE.jar"]