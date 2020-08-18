FROM openjdk:8u212-jre
MAINTAINER JacksonTu 674717739@qq.com

COPY ./target/bright-server-test-1.0.0.RELEASE.jar /bright/bright-server-test-1.0.0.RELEASE.jar
ADD agent/ /agent

ENTRYPOINT ["java", "-javaagent:/agent/skywalking-agent.jar", "-Dskywalking.agent.service_name=bright-server-test", "-Dskywalking.collector.backend_service=skywalkingIp:11800", "-jar", "/bright/bright-server-test-1.0.0.RELEASE.jar"]