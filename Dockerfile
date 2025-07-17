FROM eclipse-temurin:17-jdk as build

COPY . /app
WORKDIR /app

RUN chmod +x mvnw
RUN ./mvnw package -DskipTests
RUN mv -f target/*.jar productsada.jar

FROM eclipse-temurin:17-jre

ARG PORT
ENV PORT=8084

COPY --from=build /app/productsada.jar .

RUN useradd runtime
USER runtime

ENTRYPOINT [ "java", "-Dserver.port=8084", "-jar", "productsada.jar" ]