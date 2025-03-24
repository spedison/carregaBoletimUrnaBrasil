# carrega Boletim de Urna das Eleições do Brasil em base de dados para tratamento.

1) Onde estão os dados ?
* Use a página do TSE : https://dadosabertos.tse.jus.br/dataset/resultados-2022-boletim-de-urna

3) Como baixar os dados?
* Use o plugin para os navegadores Google Chrome ou Vivaldi chamado "DownThenAll!" em https://www.downthemall.org
  
5) Mas ele mostra todos os links, o que faço ?
* Filtre somente por arquivos .zip (Compactados)
  
6) Os arquivos são copiados para a pasta de downloads, remova os arquivos para uma pasta de trabalho.

7) O que temos de informação no nome do arquivo :
    Exemplo: bweb_1t_AL_051020221321.zip
   
          |-----------|-----------------------| 
          |   Campos  | Significado           |
          |-----------|-----------------------|
          | bweb_     | Nome fixo             |
          | 1t        | Turno 1t ou 2t        |
          | _AL_      | Sigla do Estado       |
          | 05102022  | Data do processamento |
          | 1321      | Hora do Processamento |
          | .zip      | Extensão do Arquivo   |         

8) Quais arquivos tem nesse zip ?

$ unzip -l bweb_1t_AL_051020221321.zip 
Archive:  bweb_1t_AL_051020221321.zip
  Length      Date    Time    Name
---------  ---------- -----   ----
367008246  2022-10-05 13:24   bweb_1t_AL_051020221321.csv
   337455  2020-11-18 17:13   leiame-boletimurnaweb.pdf
---------                     -------
367345701                     2 files

Vamos usar somente o arquivo: bweb_1t_AL_051020221321.csv

9) Formato do Arquivo:

O formato do arquivo está no arquivo : leiame-boletimurnaweb.pdf

O encoding utilizado é latim 1 segundo a especificação.

10) A partir desse ponto, podemos iniciar o nosso processo para colocar esses dados no banco SQL usando JPA.
