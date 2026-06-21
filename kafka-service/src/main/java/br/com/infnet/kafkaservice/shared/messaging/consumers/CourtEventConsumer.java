package br.com.infnet.kafkaservice.shared.messaging.consumers;

import br.com.infnet.kafkaservice.courts.usescases.CourtUseCase;
import br.com.infnet.kafkaservice.shared.messaging.dtos.CourtEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourtEventConsumer {

    private final CourtUseCase courtUseCase;

    @KafkaListener(topics = {"court-created", "court-updated"}, groupId = "kafka-service")
    public void consume(CourtEvent event) {
        courtUseCase.index(event);
    }
}
