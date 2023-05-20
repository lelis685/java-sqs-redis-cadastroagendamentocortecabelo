package br.com.cadastro.corte.dataprovider.repository.data;

import lombok.Getter;

@Getter
public enum TipoCorteCabelo {

    CHANEL("chanel", 20),
    FRANJA("franja", 25),
    DEGRADE("degrade", 29);

    final String descricao;
    final int valor;

    TipoCorteCabelo(String descricao, int valor) {
        this.descricao = descricao;
        this.valor = valor;
    }
}
