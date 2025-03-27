package br.com.spedison;

import br.com.spedison.processadores.ProcessadorCSV;
import br.com.spedison.processadores.ProcessadorLinhaCSV;
import br.com.spedison.utils.ConexaoJPA;
import br.com.spedison.utils.DescompactaArquivo;
import br.com.spedison.utils.IntegerUtils;
import br.com.spedison.vo.Arquivo;
import br.com.spedison.vo.BoletimUrna;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private AtomicInteger quantidadeDeRegistrosAdicionados = new AtomicInteger(0);
    private AtomicInteger contagemDeArquivosProcessados = new AtomicInteger(0);

    void processaZip(Arquivo arquivoZip) {
        // Abre conexão com o JPA.
        // Ela é closeable então, não precisamos chamar o close.
        try (ConexaoJPA conexaoJPA = new ConexaoJPA()) {
            conexaoJPA.beginTransaction();
            final int[] posicao = {1};
            //TODO: Criar classe para gravar a linha no banco, sem usar a função lambda.
            ProcessadorLinhaCSV procLinha = new ProcessadorLinhaCSV(
                    (BoletimUrna boletimUrna) -> {

                        int c = quantidadeDeRegistrosAdicionados.incrementAndGet();

                        conexaoJPA.grava(boletimUrna);
                        // a cada mil linhas, realiza um commit e retoma um nova transação.
                        if (posicao[0] % 25_000 == 0) {
                            conexaoJPA.commitTransaction();
                            conexaoJPA.beginTransaction();
                            log.debug("Commit realizado. Registros adicionados: {}.", IntegerUtils.formatInteger(c));
                        }
                        posicao[0]++;
                    });

            ProcessadorCSV procCSV = new ProcessadorCSV(procLinha);
            DescompactaArquivo descompactaArquivo = new DescompactaArquivo();
            descompactaArquivo.setExtensoes(new String[]{"csv"});
            descompactaArquivo.setProcessaCSV(procCSV);
            descompactaArquivo.descompactaArquivos(arquivoZip);
            conexaoJPA.commitTransaction();
        } catch (IOException e) {
            log.error("Problema ao processar o arquivo zip : " + arquivoZip.getNome());
        } finally {
            contagemDeArquivosProcessados.incrementAndGet();
        }
    }

    public void processa(String[] args) {
        log.debug("Iniciando o processamento");

        File file = new File(args[0]);

        // Lista todos os arquivos zips do diretório.
        File[] filesToProcess = file.listFiles(
                (File dir, String name) -> name.toLowerCase().endsWith(".zip")
        );

        if (filesToProcess == null || filesToProcess.length == 0) {
            log.error("Nenhum arquivo .zip encontrado para processar.");
            return;
        }

        // Limpa a tabela para carregar.
        try (ConexaoJPA conn = new ConexaoJPA()) {
            conn.executaSQLNativeUpdate("truncate table boletim_urna");
        } catch (IOException e) {
            log.error("Problema ao limpar a tabela de boletim_urna : ", e);
            return;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(11);
        for (File fileToProcess : filesToProcess) {
            Arquivo arquivo = new Arquivo(fileToProcess);
            executorService.submit(() -> processaZip(arquivo));
        }

        executorService.shutdown();

        try {
            while (!executorService.awaitTermination(15, TimeUnit.SECONDS)) {
                log.info("Trabalho em andamento : {} arquivos já foram processados.",
                        contagemDeArquivosProcessados.get());
            }
        } catch (InterruptedException e) {
            log.error("Processamento interrompido.", e);
        }

        /*
        Arrays
                .stream(filesToProcess)
                //.limit(10)
                .map(Arquivo::new)
                .parallel()
                .peek(az-> log.info("processando ::: " + az))
                .forEach(this::processaZip);*/

        ConexaoJPA.terminaConexoes();
        log.debug("Processamento terminado.");
    }

    public static void main(String[] args) {
        (new Main()).processa(args);
    }
}