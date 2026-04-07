# Estágio 1: Build (Compilação)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o arquivo de configuração de dependências
COPY pom.xml .

# Baixa as dependências sem copiar o código ainda (melhora o cache do Docker)
RUN mvn dependency:go-offline

# Agora copia o código fonte e gera o jar
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime (Execução)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia o JAR gerado no estágio de build
COPY --from=build /app/target/*.jar conecta-arena.jar

# O Render exige que a aplicação use a variável $PORT
# O Spring Boot vai ler isso automaticamente se configurado no application.properties
EXPOSE 8080

# Comando para rodar com limite de memória para o plano free do Render
ENTRYPOINT ["java", "-Xmx400m", "-jar", "conecta-arena.jar"]