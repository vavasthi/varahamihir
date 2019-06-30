FROM openjdk:8-jre
MAINTAINER Vinay Avasthi <vinay@avasthi.com>
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/varahamihir/lessons-0.0.1-SNAPSHOT.jar"]
# Add Maven dependencies (not shaded into the artifact; Docker-cached)
# ADD target/lib /usr/share/rae/lib
# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/varahamihir/lessons-0.0.1-SNAPSHOT.jar