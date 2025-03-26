package br.com.spedison.utils.interfaces;

import br.com.spedison.vo.BoletimUrna;

@FunctionalInterface
public interface GravadorInterface {
    void gravarConteudo(BoletimUrna boletimUrna);
}
