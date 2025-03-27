#!/bin/bash

# Diretorio onde os arquivos serão salvos altera com a sua preferencia
OUTPUT_DIR="downloads"

# Criar o diretorio se não existir
mkdir -p "$OUTPUT_DIR"

# Base da URL
BASE_URL="https://cdn.tse.jus.br/estatistica/sead/eleicoes/eleicoes2022/buweb"

# Lista de estados brasileiros (codigos UF)
ESTADOS=(
    "AC" "AL" "AM" "AP" "BA" "CE" "DF" "ES" "GO" "MA" "MG" "MS" "MT" "PA" "PB" "PE" "PI" "PR" "RJ" "RN" "RO" "RR" "RS" "SC" "SE" "SP" "TO" "ZZ"
)

# Datas e horarios dos arquivos
DATA_HORA_1T="051020221321"  # Data e hora do 1º turno
DATA_HORA_2T="311020221535"  # Data e hora do 2º turno

# Contador para acompanhar o progresso
COUNT=1

# Loop para baixar os arquivos do 1º turno
echo "Baixando arquivos do 1º turno..."
for ESTADO in "${ESTADOS[@]}"; do
    ARQUIVO="bweb_1t_${ESTADO}_${DATA_HORA_1T}.zip"
    URL="$BASE_URL/$ARQUIVO"
    echo "Baixando arquivo $COUNT: $URL"
    wget -P "$OUTPUT_DIR" "$URL"
    ((COUNT++))
done

# Loop para baixar os arquivos do 2º turno
echo "Baixando arquivos do 2º turno..."
for ESTADO in "${ESTADOS[@]}"; do
    ARQUIVO="bweb_2t_${ESTADO}_${DATA_HORA_2T}.zip"
    URL="$BASE_URL/$ARQUIVO"
    echo "Baixando arquivo $COUNT: $URL"
    wget -P "$OUTPUT_DIR" "$URL"
    ((COUNT++))
done

echo "Download concluído. Arquivos salvos em: $OUTPUT_DIR"