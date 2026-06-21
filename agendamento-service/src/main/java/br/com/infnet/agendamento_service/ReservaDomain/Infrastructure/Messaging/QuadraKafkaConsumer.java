package br.com.infnet.agendamento_service.ReservaDomain.Infrastructure.Messaging;

import br.com.infnet.agendamento_service.ReservaDomain.Handler.QuadraEventHandler;
import br.com.infnet.agendamento_service.ReservaDomain.Model.DTO.QuadraAtualizadaEvent;
import br.com.infnet.agendamento_service.ReservaDomain.Model.DTO.QuadraRegistradaEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class QuadraKafkaConsumer {
    private final QuadraEventHandler handler;
    private final ObjectMapper objectMapper;

    public QuadraKafkaConsumer(QuadraEventHandler handler, ObjectMapper objectMapper) {
        this.handler = handler;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "court-created", groupId = "agendamento-service")
    public void consumirCriacao(String payload) throws Exception {
        QuadraRegistradaEvent event = objectMapper.readValue(payload, QuadraRegistradaEvent.class);
        handler.processarNovaQuadra(event);
    }

    @KafkaListener(topics = "court-updated", groupId = "agendamento-service")
    public void consumirAtualizacao(String payload) throws Exception {
        QuadraAtualizadaEvent event = objectMapper.readValue(payload, QuadraAtualizadaEvent.class);
        handler.processarAtualizacaoQuadra(event);
    }
}
