FROM openjdk:11
LABEL maintainer="chatapp"
ADD target/chat-0.0.1-SNAPSHOT.jar springboot-docker-chatapp.jar
ENTRYPOINT ["java","-jar","springboot-docker-chatapp.jar"]