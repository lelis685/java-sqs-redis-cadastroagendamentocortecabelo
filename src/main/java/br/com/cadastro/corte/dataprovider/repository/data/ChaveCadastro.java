package br.com.cadastro.corte.dataprovider.repository.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ChaveCadastro {
    private static final String AGGREGATOR = ":";

    private String dataAgendamento;
    private String cpf;
    private String tipoCorte;

    @Override
    public String toString() {
        LocalDateTime data = LocalDateTime.parse(dataAgendamento);

        return new StringBuilder()
                .append(data.toLocalDate().toString())
                .append(AGGREGATOR)
                .append(cpf)
                .append(AGGREGATOR)
                .append(tipoCorte)
                .toString();
    }
}
