package br.com.spedison.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Arquivo {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy HHmm");

    private String nome;
    private String extensao;
    private String caminho;
    private String estado;
    private String dataProcessamento;
    private String horaProcessamento;
    private LocalDateTime instanteDoProcessamento;
    private String turno;
    private String nomeArquivoDentroZip;

    public Arquivo(File file) {

        this.nome = file.getName();
        this.extensao = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        this.caminho = file.getAbsolutePath();
        this.dataProcessamento = null;

        Pattern pattern = Pattern.compile("_([a-z]{2})_[0-9]{12}[.]zip$");
        Matcher matcher = pattern.matcher(nome.toLowerCase());
        if (matcher.find()) {
            this.estado = matcher.group(1);
        }
        // bweb_1t_
        Pattern pattern2 = Pattern.compile("bweb_([12])t_");
        Matcher matcher2 = pattern2.matcher(nome.toLowerCase());
        if (matcher2.find()) {
            this.turno = matcher2.group(1);
        }

        Pattern pattern3 = Pattern.compile("_([0-9]{8})([0-9]{4})[.]zip");
        Matcher matcher3 = pattern3.matcher(nome.toLowerCase());
        if (matcher3.find()) {
            this.dataProcessamento = matcher3.group(1);
            this.horaProcessamento = matcher3.group(2);
            // Converter data e hora para o padrão DD/MM/YYYY HH:MM
            // Exemplo: this.dataProcessamento = "20210316";
            // Exemplo: this.horaProcessamento = "1045";
            this.instanteDoProcessamento = LocalDateTime.parse(dataProcessamento + " " + horaProcessamento, formatter);
        }
    }
}