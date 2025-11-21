# ====== BUILD STAGE ======
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia pom.xml e baixa dependências (cache otimizado)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copia o restante do projeto
COPY src ./src

# Compila o projeto
RUN mvn -B package -DskipTests


# ====== RUNTIME STAGE ======
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia o .jar gerado da etapa de build
COPY --from=build /app/target/pedido-idempotente-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
