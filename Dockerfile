# --- Estágio 1: Build do Frontend (Vite) ---
# Usamos uma imagem Node "alpine" (leve)
FROM node:20-alpine AS frontend-builder
WORKDIR /app

# Copia os arquivos do package.json
COPY src/frontend/package.json src/frontend/package-lock.json* ./

# Instala as dependências do frontend
RUN npm install

# Copia o restante do código do frontend
COPY src/frontend/ ./

# Roda o build do Vite.
# Adicionamos '-- --outDir=./dist' para forçar a saída para uma pasta 'dist' local,
# ignorando a configuração do vite.config.ts que tenta escrever fora do diretório.
RUN npm run build -- --outDir=./dist


# --- Estágio 2: Build do Backend (.jar) ---
# Usamos uma imagem Maven com o JDK 21 (do seu pom.xml)
FROM maven:3.9-eclipse-temurin-21 AS backend-builder
WORKDIR /app

# Copia o wrapper do Maven e o pom.xml
COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .

# Baixa as dependências do Maven (cria um layer de cache)
RUN ./mvnw dependency:go-offline

# Copia o código-fonte do Java
COPY src/main/ src/main/
COPY src/test/ src/test/

# --- Ponto Mágico ---
# Copia os assets do frontend (construídos no Estágio 1)
# para a pasta de recursos estáticos que o Spring Boot usará.
COPY --from=frontend-builder /app/dist /app/src/main/resources/static/_src/

# Empacota a aplicação Spring Boot.
# Pulamos os testes, pois não precisamos/queremos que rodem no build.
RUN ./mvnw clean package -DskipTests


# --- Estágio 3: Imagem Final (Runtime) ---
# Usamos a imagem JRE (Java Runtime Environment) mais leve com Java 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o .jar final construído no Estágio 2
COPY --from=backend-builder /app/target/porquinho-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080 (padrão do Spring Boot)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]