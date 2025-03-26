package br.com.spedison.processadores;

import br.com.spedison.utils.interfaces.ProcessaCSVInterface;
import br.com.spedison.utils.interfaces.ProcessaLinhaInterface;
import br.com.spedison.vo.Arquivo;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

@AllArgsConstructor
public class ProcessadorCSV implements ProcessaCSVInterface {

    private static final Logger log = LoggerFactory.getLogger(ProcessadorCSV.class);
    private ProcessaLinhaInterface processaLinha;

    @Override
    public boolean processaArquivo(Arquivo arquivo, BufferedReader inputStream) {

        // Configura Biblioteca de para leitura dos registros do CSV.
        CSVFormat.Builder builder = CSVFormat
                .Builder
                .create(CSVFormat.DEFAULT)
                .setDelimiter(";")
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                //.setEscape('\\')
                .setQuoteMode(CSVFormat.DEFAULT.getQuoteMode())
                .setNullString("")
                .setSkipHeaderRecord(false);

        try (
            CSVParser csvParser =
                    new CSVParser(inputStream, builder.build()) ){

            log.debug("Processando arquivo: " + arquivo.getNome() + " com nome no Zip de " + arquivo.getNomeArquivoDentroZip());

            for (CSVRecord record : csvParser) {
                if (record.getRecordNumber() != 1)
                    processaLinha.processaLinha(arquivo, record, csvParser.getRecordNumber());
            }
        } catch (IOException ioe) {
            log.error("Problema ao ler o arquivo CSV: " + arquivo.getNome(), ioe);
            return false;
        }finally {
            System.gc();
        }
        return true;
    }
}