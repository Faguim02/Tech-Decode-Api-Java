#!/bin/bash
echo "🚀 Atualizando repositorio GIT..."
git pull
echo "------------------------------"
echo "🛑 Derrubando containeres existentes..."
docker-compose down
docker rmi -f tech-decode-spring
docker rmi -f tech-decode-mail
echo "------------------------------"
echo "🔨 Criando a imagem..."
docker build -t tech-decode-spring ./ms-api-techdecode
docker build -t tech-decode-mail ./ms-mail-techdecode
echo "------------------------------"
echo "🐋 Subindo o container..."
docker-compose up -d
echo "✅ Concluido com sucesso!!"