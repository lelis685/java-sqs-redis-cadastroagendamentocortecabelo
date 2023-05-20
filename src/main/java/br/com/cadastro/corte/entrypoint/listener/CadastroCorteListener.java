package br.com.cadastro.corte.entrypoint.listener;


import br.com.cadastro.corte.entrypoint.listener.data.CadastroMessage;
import br.com.cadastro.corte.domain.service.CadastroCorteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CadastroCorteListener {

    private final ObjectMapper mapper;
    private final CadastroCorteService service;


    @SqsListener("${cloud.aws.queue.name}")
    public void onMessage(String rawMessage) {

        try {
            log.info("Mensagem recebida {}", rawMessage);

            CadastroMessage message = mapper.readValue(rawMessage, CadastroMessage.class);

            service.process(message);

            log.info("Mensagem processada com sucesso.");
        } catch (JsonProcessingException e) {
            log.error("Error ao processar mensagem");
        }
    }





}
