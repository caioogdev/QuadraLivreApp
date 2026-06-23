package br.com.infnet.kafkaservice.shared.messaging.consumers;

import br.com.infnet.kafkaservice.courts.usescases.CourtUseCase;
import br.com.infnet.kafkaservice.shared.messaging.dtos.CourtEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourtEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CourtEventConsumer.class);
    private final CourtUseCase courtUseCase;

    @KafkaListener(topics = {"court-created", "court-updated"}, groupId = "kafka-service")
    public void consume(CourtEvent event) {
        logger.info("Evento recebido: courtId={}", event.courtId().toString());
        try {
            courtUseCase.index(event);
            logger.info("Evento indexado com sucesso: courtId:{}", event.courtId().toString());
        } catch (Exception e) {
            logger.error("Erro ao indexar evento courtId={}", event.courtId().toString(), e);
        }
    }
}
