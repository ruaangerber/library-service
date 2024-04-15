FROM gradle:jdk21 AS build
WORKDIR /workspace/app

COPY src /workspace/app/src
COPY build.gradle /workspace/app
COPY settings.gradle /workspace/app

RUN gradle clean build
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*-SNAPSHOT.jar)

FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
RUN addgroup -S demo && adduser -S demo -G demo
RUN mkdir /app
RUN chown demo:demo /app -R
USER demo
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.awesome.library.service.LibraryServiceApplication"]