package br.com.infnet.agendamento_service.ReservaDomain.Infrastructure.Messaging;

import br.com.infnet.agendamento_service.ReservaDomain.Handler.QuadraEventHandler;
import br.com.infnet.agendamento_service.ReservaDomain.Model.DTO.QuadraAtualizadaEvent;
import br.com.infnet.agendamento_service.ReservaDomain.Model.DTO.QuadraRegistradaEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class QuadraDLTKafkaConsumer {
    private final QuadraEventHandler handler;

    public QuadraDLTKafkaConsumer(QuadraEventHandler handler) {
        this.handler = handler;
    }

    @KafkaListener(
            topics = "novo-registro-quadra-topic.DLT",
            groupId = "microsservico-reserva-dlt-group"
    )
    public void consumirErro(QuadraRegistradaEvent event) {
        System.out.println("Tentando reprocessar quadra da DLT: " + event.idQuadra());
        handler.processarNovaQuadra(event);
    }

    @KafkaListener(topics = "atualizacao-quadra-topic.DLT", groupId = "microsservico-reserva-dlt-group")
    public void consumirErroAtualizacao(QuadraAtualizadaEvent event) {
        System.out.println("Tentando reprocessar atualização de quadra vinda da DLT: " + event.idQuadra());
        handler.processarAtualizacaoQuadra(event);
    }
}
