FROM openjdk:1.8

RUN mkdir -p /app

WORKDIR /app

ADD ./target/*.jar ./target/

RUN chmod 755 -R ./target/*.jar

EXPOSE 9080

ENTRYPOINT ["java","-jar","target/stkpay-0.0.1-SNAPSHOT.jar"]