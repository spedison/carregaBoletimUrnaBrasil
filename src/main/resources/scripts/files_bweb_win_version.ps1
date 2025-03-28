$OutputDir = "downloads"

if (!(Test-Path -Path $OutputDir)) {
    New-Item -ItemType Directory -Path $OutputDir
}

$BaseUrl = "https://cdn.tse.jus.br/estatistica/sead/eleicoes/eleicoes2022/buweb"

$Estados = @(
    "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO", "ZZ" 
)

$DataHora1T = "051020221321"  
$DataHora2T = "311020221535"  

$Count = 1

Write-Host "Baixando arquivos do 1º turno..."
foreach ($Estado in $Estados) {
    $Arquivo = "bweb_1t_${Estado}_${DataHora1T}.zip"
    $Url = "$BaseUrl/$Arquivo"
    Write-Host "Baixando arquivo $Count: $Url"
    Invoke-WebRequest -Uri $Url -OutFile "$OutputDir\$Arquivo"
    $Count++
}

Write-Host "Baixando arquivos do 2º turno..."
foreach ($Estado in $Estados) {
    $Arquivo = "bweb_2t_${Estado}_${DataHora2T}.zip"
    $Url = "$BaseUrl/$Arquivo"
    Write-Host "Baixando arquivo $Count: $Url"
    Invoke-WebRequest -Uri $Url -OutFile "$OutputDir\$Arquivo"
    $Count++
}

Write-Host "Download concluído. Arquivos salvos em: $OutputDir"