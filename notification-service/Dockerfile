FROM eclipse-temurin:17-jdk
COPY --chown=root:root applications/app-service/build/libs/NotificationService.jar NotificationService.jar
RUN sh -c 'touch /NotificationService.jar'

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

USER root

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar NotificationService.jar" ]