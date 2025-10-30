FROM eclipse-temurin:21-jdk

# diretório de trabalho no container
WORKDIR /app

# instala Maven (caso queira rodar dentro do container)
RUN apt-get update && apt-get install -y maven

# copia pom.xml e baixa dependências para cache
COPY pom.xml .
RUN mvn dependency:go-offline

# copia o código fonte
COPY src ./src

# expõe a porta padrão do Spring Boot
EXPOSE 8080

# comando padrão
CMD ["mvn", "spring-boot:run"]