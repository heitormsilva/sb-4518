FROM eclipse-temurin:17-jre-alpine AS builder
ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract --destination extracted

FROM eclipse-temurin:17-jre-alpine
ARG APP_DIR=/opt/app
ARG BUILDER_DIR=${APP_DIR}/extracted/
WORKDIR ${APP_DIR}
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=builder ${BUILDER_DIR}/dependencies/ ./
COPY --from=builder ${BUILDER_DIR}/snapshot-dependencies/ ./
COPY --from=builder ${BUILDER_DIR}/spring-boot-loader/ ./
COPY --from=builder ${BUILDER_DIR}/application/ ./
ENTRYPOINT java "org.springframework.boot.loader.JarLauncher"