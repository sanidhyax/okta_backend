
FROM --platform=linux/amd64 azul/zulu-openjdk:17-latest
COPY /target/extenddummyjson-0.0.1-SNAPSHOT.jar extenddummyjson.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","extenddummyjson.jar"]