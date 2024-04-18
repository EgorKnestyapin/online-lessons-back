# build step
# maven - this is a Linux + Maven image
FROM maven as build
# create a folder inside this Linux and call it app
WORKDIR /workspace/app
# copy our pom.xml to this folder
COPY pom.xml .
# We also copy our source code to this folder
COPY src src
# run maven to build
RUN mvn clean package
# create a folder called dependency and copy into it everything that is inside our jar and has the jar extension
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# move on to the next step
# we take linux, which no longer has maven and other garbage, but only JRE (Virtual machine)
FROM eclipse-temurin:17-jre-alpine
# named our dependency folder DEPENDENCY
ARG DEPENDENCY=/workspace/app/target/dependency
# from the previous step we take all the dependencies and copy them to the new linux
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
# launch the application along with all the libraries
ENTRYPOINT ["java","-cp","app:app/lib/*", "de.aittr.online_lessons.OnlineLessonsApplication"]