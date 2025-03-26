package br.com.spedison.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@lombok.Data
@lombok.NoArgsConstructor
@Table(name = "boletim_urna")
public class BoletimUrna {

    @Id
    @Unique
    @Column(length = 36)
    private String id; // Usar a biblioteca que gere um UUID. (Para aumentar a performance do insert)

    // Metadados do Arquivo
    private String nomeArquivoZipado;
    private String nomeArquivoDentroZip;
    private LocalDateTime horaProcessamento;
    private String turno;
    private String sgUFArquivo;
    private Long numeroLinha;

    // Campos do CSV.
    private String dtGeracao;
    private String hhGeracao;
    private Integer anoEleicao;
    //TODO: Transformar esse código em uma Enum.
    private Short cdTipoEleicao; // CD_TIPO_ELEICAO 0-Eleição Ordinária, 1-Eleição Extraordinária, 2-Consulta Popular
    @Column(length = 128)
    private String nmTipoEleicao; // NM_TIPO_ELEICAO
    private Integer cdPleito; // CD_PLEITO Cód do Pleito. 1 Pleito pode ter mais de uma eleição.
    //TODO: Converter todos os campos de data/hora para objetos de LocalDateTime e compatíveis.
    private String dtPleito; // DT_PLEITO
    private Short nrTurno; // NR_TURNO
    private Integer cdEleicao; // CD_ELEICAO Este código é único por eleição e por turno
    @Column(length = 1024)
    private String dsEleicao; // DS_ELEICAO
    private String sgUF;
    private Integer cdMunicipio; // CD_MUNICIPIO
    @Column(length = 1024)
    private String nmMunicipio; // NM_MUNICIPIO
    private Short nrZona; // NR_ZONA
    private Short nrSecao; // NR_SECAO
    private Integer nrLocalVotacao; // NR_LOCAL_VOTACAO
    private Integer cdCargoPergunta; // CD_CARGO_PERGUNTA

    @Column(length = 1024)
    private String dsCargoPergunta; // DS_CARGO_PERGUNTA
    private Integer nrPartido; // NR_PARTIDO;

    @Column(length = 128)
    private String sgPartido; // SG_PARTIDO
    @Column(length = 1024)
    private String nmPartido; // NM_PARTIDO
    private String dtBuRecebido; // DT_BU_RECEBIDO
    private Integer qtAptos; // QT_APTOS
    private Integer qtComparecimento; // QT_COMPARECIMENTO
    private Integer qtAbstencoes; // QT_ABSTENCOES
    private Integer cdTipoUrna;// CD_TIPO_URNA
    private String dsTipoUrna; // DS_TIPO_URNA
    private Integer cdTipoVotavel; // CD_TIPO_VOTAVEL
    private String dsTipoVotavel; // DS_TIPO_VOTAVEL
    /***
     * Pode assumir os valores: Número do candidato (quando
     * voto nominal); Número do partido (quando voto em
     * legenda); Número 95 (quando voto em branco); Número 96
     * (quando voto nulo); Número 97 (quando voto anulado e
     * apurado em separado) e Número 98(quando voto anulado).
     */
    private Integer nrVotavel; // NR_VOTAVEL
    /***
     * Pode assumir os valores: Nome do candidato (quando voto
     * nominal ou voto anulado); Nome do partido (quando voto
     * em legenda); "Voto em branco" (quando voto em branco);
     * "Voto Nulo" (quando voto nulo) e "Voto anulado e apurado
     * em separado" (quando voto anulado e apurado em
     * separado).
     */
    private String nmVotavel; // NM_VOTAVEL
    private Integer qtVotos; // QT_VOTOS
    private Integer nrUrnaEfetivada; // NR_URNA_EFETIVADA
    @Column(length = 128)
    private String cdCarga1UrnaEfetivada; // CD_CARGA_1_URNA_EFETIVADA
    @Column(length = 128)
    private String cdCarga2UrnaEfetivada; // CD_CARGA_2_URNA_EFETIVADA
    private String cdFlashcardUrnaEfetivada; // CD_FLASHCARD_URNA_EFETIVADA
    private String dtCargaUrnaEfetivada;// DT_CARGA_URNA_EFETIVADA
    private String dsCargoPerguntaSecao; // DS_CARGO_PERGUNTA_SECAO

    @Column(length = 1024)
    private String dsAgregadas; //  DS_AGREGADAS
    private String dtAbertura; // DT_ABERTURA
    private String dtEncerramento; // DT_ENCERRAMENTO
    /***
     * Quantitativo de eleitores com biometria, mas que não foram
     * habilitados por meio dela.
     */
    private Integer qtEleitoresBiometriaNH; // QT_ELEITORES_BIOMETRIA_NH
    private String dtEmissaoBU; // DT_EMISSAO_BU
    private Integer nrJuntaApuradora; // NR_JUNTA_APURADORA
    private Integer nrTurmaApuradora; // NR_TURMA_APURADORA
}
