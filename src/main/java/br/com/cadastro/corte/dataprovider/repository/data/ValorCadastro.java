package br.com.cadastro.corte.dataprovider.repository.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValorCadastro {

    private String nomeCliente;
    private String dataHoraAgendamento;
    private String tipoCorte;
    private int valor;

}
