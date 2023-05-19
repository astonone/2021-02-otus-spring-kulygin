FROM maven:3.8.1-openjdk-11
ENV PROJECT_DIR=/opt/library

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY . .
RUN mvn clean install

ADD ./ $PROJECT_DIR/

FROM openjdk:11-jdk

ENV PROJECT_DIR=/opt/library
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/ht17-server/target/ht17-server*.jar $PROJECT_DIR/library.jar

ENTRYPOINT ["java", "-jar", "/opt/library/library.jar"]

EXPOSE 8080