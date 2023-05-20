package br.com.cadastro.corte.entrypoint.listener.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CadastroMessage {

    private String nomeCliente;
    private String cpf;
    private String dataHoraAgendamento;
    private String tipoCorte;


    public String getDataAgendamento(){
        return LocalDateTime
                .parse(this.dataHoraAgendamento)
                .toLocalDate().toString();
    }

}
