package br.com.cadastro.corte.dataprovider.repository.data;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ValorCadastro {

    private String nomeCliente;
    private String dataHoraAgendamento;
    private String tipoCorte;
    private int valor;

}
