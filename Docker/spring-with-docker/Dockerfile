FROM amazoncorretto:18
ARG JAR_FILE=./Docker/spring-with-docker/build/libs/*.jar
COPY ${JAR_FILE} spring-with-docker.jar
ENTRYPOINT ["sh","-c","java  ${JAVA_OPTS} -jar /spring-with-docker.jar"]
CMD ["bash"]
EXPOSE 8080

