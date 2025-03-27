package br.com.spedison.utils;

import br.com.spedison.utils.interfaces.ProcessaCSVInterface;
import br.com.spedison.vo.Arquivo;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

@Data
public class DescompactaArquivo {

    private static final Logger log = LoggerFactory.getLogger(DescompactaArquivo.class);
    private ProcessaCSVInterface processaCSV;
    private String[] extensoes;

    private boolean aceitarExtensoes(String nomeArquivo) {
        for (String extensao : extensoes) {
            if (nomeArquivo.toLowerCase().endsWith(extensao)) {
                return true;
            }
        }
        return false;
    }

    private List<String> listaArquivosNoZip(Arquivo arquivo) {
        List<String> ret = new LinkedList<>();
        Path zipPath = Paths.get(arquivo.getCaminho());
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath), StandardCharsets.UTF_8)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                boolean extOk = aceitarExtensoes(entry.getName());
                boolean isDirectory = entry.isDirectory();
                if (extOk && !isDirectory)
                    ret.add(entry.getName());
            }
        } catch (ZipException e) {
            log.error("Problemas ao listar arquivos do Zip.", e);
        } catch (IOException e) {
            log.error("Problemas ao arquivo Zip.", e);
        }
        return ret;
    }

    public void processaUmArquivo(Arquivo arquivo, String arquivoParaProcessar) {
        Path zipPath = Paths.get(arquivo.getCaminho());
        boolean foiProcessado = false;

        try {
            ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath), StandardCharsets.UTF_8);
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {

                if (!entry.getName().equals(arquivoParaProcessar)) continue;
                if (entry.isDirectory()) continue;

                foiProcessado = true;

                arquivo.setNomeArquivoDentroZip(entry.getName());
                log.debug("Lendo arquivo: " + entry.getName() + " dentro do Zip:" + arquivo.getNome());

                //Processa o Stream do arquivo compactado. Cuidado pois o arquivo é latim1.
                BufferedReader streamFile = new BufferedReader(new InputStreamReader(zipInputStream, StandardCharsets.ISO_8859_1));
                boolean ret = processaCSV.processaArquivo(arquivo, streamFile);
                //mantive o Stream do arquivo aberto, pois o que manda é o Stream Superior (o que vem so Zip)
                if (!ret) {
                    log.error("Erro ao processar o arquivo: " + entry.getName());
                    streamFile.close();
                }
                break;
            } // Fim do While na lista de arquivos do Zip.
            if (!foiProcessado)
                zipInputStream.close();
            System.gc();
        } catch (IOException e) {
            log.error("Erro no processamento do arquivo descompactado.", e);
            return;
        }
    }

    public void descompactaArquivos(Arquivo arquivo) {
        List<String> arquivosParaProcessarDentroDoZip = listaArquivosNoZip(arquivo);
        for (String arquivoParaProcessar : arquivosParaProcessarDentroDoZip) {
            log.debug("Arquivo para processar: " + arquivoParaProcessar);
            processaUmArquivo(arquivo, arquivoParaProcessar);
        }
    }
}