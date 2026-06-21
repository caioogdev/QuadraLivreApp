package br.com.infnet.catalogoservice.shared.messaging.producers;

import br.com.infnet.catalogoservice.shared.messaging.dtos.CourtEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourtProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(CourtEvent event) {
        kafkaTemplate.send("court-created", event.courtId().toString(), event);
    }

    public void publishUpdated(CourtEvent event) {
        kafkaTemplate.send("court-updated", event.courtId().toString(), event);
    }
}
