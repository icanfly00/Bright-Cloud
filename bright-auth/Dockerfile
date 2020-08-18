FROM openjdk:8u212-jre
MAINTAINER JacksonTu 674717739@qq.com

COPY ./target/bright-auth-1.0.0.RELEASE.jar /bright/bright-auth-1.0.0.RELEASE.jar
ADD agent/ /agent

ENTRYPOINT ["java", "-javaagent:/agent/skywalking-agent.jar", "-Dskywalking.agent.service_name=bright-auth", "-Dskywalking.collector.backend_service=skywalkingIp:11800", "-jar", "/bright/bright-auth-1.0.0.RELEASE.jar"]