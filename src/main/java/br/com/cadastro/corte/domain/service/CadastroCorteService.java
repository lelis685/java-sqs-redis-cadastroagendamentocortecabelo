package br.com.cadastro.corte.domain.service;

import br.com.cadastro.corte.dataprovider.repository.CadastroCorteRepository;
import br.com.cadastro.corte.dataprovider.repository.data.ChaveCadastro;
import br.com.cadastro.corte.dataprovider.repository.data.ValorCadastro;
import br.com.cadastro.corte.entrypoint.listener.data.CadastroMessage;
import br.com.cadastro.corte.dataprovider.repository.data.TipoCorteCabelo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastroCorteService {

    private final CadastroCorteRepository repository;

    public void process(CadastroMessage message) {
        findTipoCorteCabelo(message.getTipoCorte())
                .ifPresentOrElse(
                        (tipoCorte) -> salvar(message, tipoCorte),
                        () -> log.warn("Tipo de corte nao encontrado.")
                );

    }

    private void salvar(CadastroMessage message, TipoCorteCabelo tipoCorteCabelo) {
        try {
            String chave = generateKey(message);

            ValorCadastro valorCadastro = ValorCadastro.builder()
                    .tipoCorte(tipoCorteCabelo.getDescricao())
                    .valor(tipoCorteCabelo.getValor())
                    .nomeCliente(message.getNomeCliente())
                    .dataHoraAgendamento(message.getDataHoraAgendamento())
                    .build();

            repository.save(chave, valorCadastro);
            log.info("Chave {} Valor {}", chave, valorCadastro);
        } catch (JsonProcessingException e) {
            log.error("Error ao salvar mensagem.", e);
            throw new RuntimeException(e);
        }
    }


    private String generateKey(CadastroMessage message) {
        return ChaveCadastro.builder()
                .dataAgendamento(message.getDataAgendamento())
                .cpf(message.getCpf())
                .tipoCorte(message.getTipoCorte())
                .build()
                .toString();
    }

    private Optional<TipoCorteCabelo> findTipoCorteCabelo(String tipoCorteCabelo) {
        return Arrays.stream(TipoCorteCabelo.values())
                .filter((tipoCorte) -> tipoCorte.getDescricao().equals(tipoCorteCabelo))
                .findAny();
    }


    public ValorCadastro findByKey(String tipoCorte, String cpf, LocalDate data) throws JsonProcessingException {
        String chave = ChaveCadastro.builder()
                .dataAgendamento(data.toString())
                .cpf(cpf)
                .tipoCorte(tipoCorte).build().toString();

        return repository.findByKey(chave);
    }
}
