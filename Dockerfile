FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia o JAR gerado no estágio de build
COPY --from=build /app/target/*.jar conecta-arena.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx400m", "-jar", "conecta-arena.jar"]