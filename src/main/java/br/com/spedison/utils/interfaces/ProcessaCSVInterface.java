package br.com.spedison.utils.interfaces;

import br.com.spedison.vo.Arquivo;

import java.io.BufferedReader;

@FunctionalInterface
public interface ProcessaCSVInterface {
    boolean processaArquivo(Arquivo arquivo, BufferedReader inputStream);
}