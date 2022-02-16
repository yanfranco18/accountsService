FROM openjdk:11
VOLUME /tmp
EXPOSE 8095
ADD ./target/accounts-0.0.1-SNAPSHOT.jar account-service.jar
ENTRYPOINT ["java","-jar","/account-service.jar"]