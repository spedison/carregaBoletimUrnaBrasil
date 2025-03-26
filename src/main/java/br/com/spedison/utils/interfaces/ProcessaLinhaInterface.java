package br.com.spedison.utils.interfaces;

import br.com.spedison.vo.Arquivo;
import org.apache.commons.csv.CSVRecord;

@FunctionalInterface
public interface ProcessaLinhaInterface {
    void processaLinha(Arquivo arquivo, CSVRecord linha, long numeroLinha);
}