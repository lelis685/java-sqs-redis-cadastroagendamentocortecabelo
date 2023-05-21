package br.com.cadastro.corte.entrypoint.listener;


import br.com.cadastro.corte.domain.service.CadastroCorteService;
import br.com.cadastro.corte.entrypoint.listener.data.CadastroMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CadastroCorteListener {

    private ObjectMapper mapper;
    private CadastroCorteService service;

    private Counter messageCounter;
    private Counter successMessageCounter;
    private Counter errorMessageCounter;

    public CadastroCorteListener(ObjectMapper mapper, CadastroCorteService service, MeterRegistry meterRegistry) {
        this.mapper = mapper;
        this.service = service;

        messageCounter = Counter.builder("messages")
                .tag("tipo", "total")
                .description("Total Mensagens recebidas")
                .register(meterRegistry);

        successMessageCounter = Counter.builder("messages")
                .tag("tipo", "sucesso")
                .description("Total Mensagens processadas com sucesso.")
                .register(meterRegistry);

        errorMessageCounter = Counter.builder("messages")
                .tag("tipo", "error")
                .description("Total Mensagens com error.")
                .register(meterRegistry);
    }

    @Timed(value = "duration.process.message",histogram = true)
    @SqsListener("${cloud.aws.queue.name}")
    public void onMessage(String rawMessage) {

        try {
            log.info("Mensagem recebida {}", rawMessage);

            messageCounter.increment();

            CadastroMessage message = mapper.readValue(rawMessage, CadastroMessage.class);

            service.process(message);

            log.info("Mensagem processada com sucesso.");

            successMessageCounter.increment();

        } catch (JsonProcessingException e) {
            log.error("Error ao processar mensagem");
            errorMessageCounter.increment();
        }
    }


}
