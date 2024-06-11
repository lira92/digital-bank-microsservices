# Use uma imagem base oficial do Python
FROM python:3.10-slim

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo de requisitos para o diretório de trabalho
COPY requirements.txt .

# Instala as dependências do Python
RUN pip install --no-cache-dir -r requirements.txt

# Copia o restante da aplicação para o diretório de trabalho
COPY . .

# Comando para rodar a aplicação com uvicorn e auto-reload
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000", "--reload"]
