package br.com.cadastro.corte.entrypoint.listener.data;

import lombok.Data;

@Data
public class CadastroMessage {

    private String nomeCliente;
    private String cpf;
    private String dataHoraAgendamento;
    private String tipoCorte;

}
