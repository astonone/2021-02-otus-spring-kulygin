FROM maven:3.8.1-openjdk-11
ENV PROJECT_DIR=/opt/interviewer

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

ADD ./ $PROJECT_DIR/

FROM openjdk:11-jdk

ENV PROJECT_DIR=/opt/interviewer
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/project-server/target/project-server* $PROJECT_DIR

ENTRYPOINT ["java", "-jar", "/opt/interviewer/project-server-1.0-SNAPSHOT.jar"]

EXPOSE 8080