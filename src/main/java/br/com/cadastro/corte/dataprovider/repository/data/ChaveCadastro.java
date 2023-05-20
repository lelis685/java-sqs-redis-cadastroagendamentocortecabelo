package br.com.cadastro.corte.dataprovider.repository.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
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
        LocalDate data = LocalDate.parse(dataAgendamento);

        return new StringBuilder()
                .append(data.toString())
                .append(AGGREGATOR)
                .append(cpf)
                .append(AGGREGATOR)
                .append(tipoCorte)
                .toString();
    }
}
