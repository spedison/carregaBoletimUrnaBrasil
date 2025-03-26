package br.com.spedison.processadores;

import br.com.spedison.utils.IntegerUtils;
import br.com.spedison.utils.interfaces.GravadorInterface;
import br.com.spedison.utils.interfaces.ProcessaLinhaInterface;
import br.com.spedison.vo.Arquivo;
import br.com.spedison.vo.BoletimUrna;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class ProcessadorLinhaCSV implements ProcessaLinhaInterface {

    GravadorInterface gravadorInterface;

    @Override
    public void processaLinha(Arquivo arquivo, CSVRecord linha, long numeroLinha) {

        BoletimUrna bo = new BoletimUrna();

        //Identificador único
        UUID uuid = UUID.randomUUID();
        bo.setId(uuid.toString());

        // Metadados do Arquivo (podemos colcoar o Hash também)
        bo.setNomeArquivoZipado(arquivo.getNome());
        bo.setNomeArquivoDentroZip(arquivo.getNomeArquivoDentroZip());
        bo.setHoraProcessamento(LocalDateTime.now());
        bo.setTurno(arquivo.getTurno());
        bo.setSgUFArquivo(arquivo.getEstado());
        bo.setNumeroLinha(numeroLinha);

        // Dados do CSV.
        int col = 0;
        bo.setDtGeracao(linha.get(col++));
        bo.setHhGeracao(linha.get(col++));
        bo.setAnoEleicao(IntegerUtils.parseInt(linha.get(col++)));
        bo.setCdTipoEleicao(Short.parseShort(linha.get(col++)));
        bo.setNmTipoEleicao(linha.get(col++));
        bo.setCdPleito(IntegerUtils.parseInt(linha.get(col++)));
        bo.setDtPleito(linha.get(col++));
        bo.setNrTurno(Short.parseShort(linha.get(col++)));
        bo.setCdEleicao(IntegerUtils.parseInt(linha.get(col++)));
        bo.setDsEleicao(linha.get(col++));
        bo.setSgUF(linha.get(col++));
        bo.setCdMunicipio(IntegerUtils.parseInt(linha.get(col++)));
        bo.setNmMunicipio(linha.get(col++));
        bo.setNrZona(Short.parseShort(linha.get(col++)));
        bo.setNrSecao(Short.parseShort(linha.get(col++)));
        bo.setNrLocalVotacao(IntegerUtils.parseInt(linha.get(col++)));
        bo.setCdCargoPergunta(IntegerUtils.parseInt(linha.get(col++)));
        bo.setDsCargoPergunta(linha.get(col++));
        bo.setNrPartido(IntegerUtils.parseInt(linha.get(col++)));
        bo.setSgPartido(linha.get(col++));
        bo.setNmPartido(linha.get(col++));
        bo.setDtBuRecebido(linha.get(col++));
        bo.setQtAptos(IntegerUtils.parseInt(linha.get(col++)));
        bo.setQtComparecimento(IntegerUtils.parseInt(linha.get(col++)));
        bo.setQtAbstencoes(IntegerUtils.parseInt(linha.get(col++)));
        bo.setCdTipoUrna(IntegerUtils.parseInt(linha.get(col++)));
        bo.setDsTipoUrna(linha.get(col++));
        bo.setCdTipoVotavel(IntegerUtils.parseInt(linha.get(col++)));
        bo.setDsTipoVotavel(linha.get(col++));
        bo.setNrVotavel(IntegerUtils.parseInt(linha.get(col++)));
        bo.setNmVotavel(linha.get(col++));
        bo.setQtVotos(IntegerUtils.parseInt(linha.get(col++)));
        bo.setNrUrnaEfetivada(IntegerUtils.parseInt(linha.get(col++)));
        bo.setCdCarga1UrnaEfetivada(linha.get(col++));
        bo.setCdCarga2UrnaEfetivada(linha.get(col++));
        bo.setCdFlashcardUrnaEfetivada(linha.get(col++));
        bo.setDtCargaUrnaEfetivada(linha.get(col++));
        bo.setDsCargoPerguntaSecao(linha.get(col++));
        bo.setDsAgregadas(linha.get(col++));
        bo.setDtAbertura(linha.get(col++));
        bo.setDtEncerramento(linha.get(col++));
        bo.setQtEleitoresBiometriaNH(IntegerUtils.parseInt(linha.get(col++)));
        bo.setDtEmissaoBU(linha.get(col++));
        bo.setNrJuntaApuradora(IntegerUtils.parseInt(linha.get(col++)));
        bo.setNrTurmaApuradora(IntegerUtils.parseInt(linha.get(col++)));

        gravadorInterface.gravarConteudo(bo);
    }
}
