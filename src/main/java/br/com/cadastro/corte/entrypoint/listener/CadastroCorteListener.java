package br.com.cadastro.corte.entrypoint.listener;


import br.com.cadastro.corte.domain.service.CadastroCorteService;
import br.com.cadastro.corte.entrypoint.listener.data.CadastroMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.prometheus.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.util.Random;


@Slf4j
@Component
public class CadastroCorteListener {

    private ObjectMapper mapper;
    private CadastroCorteService service;

    private final Counter counter;
    private final Histogram histogram;
    private final Summary summary;
    private final Gauge gauge;

    public CadastroCorteListener(ObjectMapper mapper, CadastroCorteService service, CollectorRegistry collectorRegistry) {
        this.mapper = mapper;
        this.service = service;

        counter = Counter.build()
                .name("messages")
                .help("Number of sqs messages")
                .labelNames("tipo")
                .register(collectorRegistry);

        histogram = Histogram.build()
                .name("duration_histogram_process_message")
                .help("Duration process sqs message.")
                .buckets(0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07)
                .register(collectorRegistry);

        summary = Summary.build()
                .name("duration_process_message_seconds")
                .help("Duration process sqs message.")
                .quantile(0.5, 0.001)
                .quantile(0.9, 0.001)
                .quantile(0.95, 0.001)
                .quantile(0.99, 0.001)
                .register(collectorRegistry);

        gauge = Gauge.build()
                .name("message_size")
                .help("Message size")
                .register(collectorRegistry);

    }

    @SqsListener("${cloud.aws.queue.name}")
    public void onMessage(String rawMessage) {
        Histogram.Timer timer = histogram.startTimer();
        Summary.Timer timerSummary = summary.startTimer();
        long startTime = System.nanoTime();
        try {
            log.info("Mensagem recebida {}", rawMessage);
            counter.labels("total").inc();
            gauge.set(new Random().nextInt(10, 1000));

            CadastroMessage message = mapper.readValue(rawMessage, CadastroMessage.class);

            service.process(message);

            log.info("Mensagem processada com sucesso.");

            counter.labels("sucesso").inc();

        } catch (JsonProcessingException e) {
            log.error("Error ao processar mensagem");
            counter.labels("error").inc();

        } finally {
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;

            log.info("Duration Java {}", duration);
            log.info("Duration Summary {}", timerSummary.observeDuration());
            log.info("Duration Histogram {}", timerSummary.observeDuration());
            timerSummary.observeDuration();
            timer.observeDuration();
        }
    }


}
