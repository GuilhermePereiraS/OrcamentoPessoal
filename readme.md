Projeto Porquinho - Guia Rápido de Comandos

Este projeto é configurado para rodar de duas formas:

Modo Híbrido (Dev Rápido): Banco no Docker, Aplicação rodando localmente (IDE/Maven) com frontend em (Vite).

Modo Docker Total (Produção): Tudo (App + Banco) rodando em containers via docker-compose.

Pré-requisitos

Java (JDK 21+)

Node.js (com NPM)

Docker e Docker Compose

🚀 Workflow 1: Modo Híbrido (Desenvolvimento Rápido)

Ideal para: Desenvolver o backend (Java) e frontend (TS/SCSS) com hot-reload.

1. Inicie o Banco de Dados

Inicie apenas o postgres e o pgadmin.

sudo docker-compose up -d postgres pgadmin


2. Rode o Frontend (Vite)

Terminal 1: Inicia o servidor Vite para hot-reload.

cd src/frontend
npm install
npm run dev


3. Rode o Backend (Spring)

Terminal 2: Rode a aplicação Spring Boot pela sua IDE (IntelliJ / VS Code) ou pelo terminal.

./mvnw spring-boot:run


A aplicação (rodando em localhost:8080) vai se conectar ao banco (em localhost:5433) usando as configurações padrão do application.properties.

📦 Workflow 2: Modo Docker Total (Simulação de Produção)

Ideal para: Testar o build completo ou rodar o projeto em um ambiente de produção.

1. Construir e Rodar Tudo

Este comando vai:

Construir a imagem do frontend (npm run build).

Construir a imagem do backend (./mvnw clean package).

Criar uma imagem final da aplicação.

Subir os containers app, postgres e pgadmin.

sudo docker-compose up --build


Para rodar em background: sudo docker-compose up -d --build

Acesso: A aplicação estará em http://localhost:8080.

🛠 Comandos de Gerenciamento

Parar Tudo

Para os containers app, postgres e pgadmin. Os dados do banco (volume) são mantidos.

sudo docker-compose down


Resetar o Banco de Dados

Para os containers E apaga permanentemente os dados do banco.

sudo docker-compose down -v


Rodar Testes (Maven)

Requer que o banco esteja no ar (use o Workflow 1 para iniciar o postgres):

# 1. Garanta que o banco esteja no ar:
sudo docker-compose up -d postgres

# 2. Rode os testes
./mvnw test
