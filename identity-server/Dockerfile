FROM harisekhon/ubuntu-java
ARG SERVICE_NAME
ARG JAR_FILE

VOLUME /tmp

COPY ${JAR_FILE} app.jar

ENV JAVA_OPTS="-Xmx128m -Xms64m"
ENV SERVICE_NAME=${SERVICE_NAME}
ENV SPRING_PROFILES_ACTIVE="docker"
LABEL APP=${SERVICE_NAME}
LABEL DOMAIN="varahamihir"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]