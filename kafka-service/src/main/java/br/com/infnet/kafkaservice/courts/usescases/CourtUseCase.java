package br.com.infnet.kafkaservice.courts.usescases;

import br.com.infnet.kafkaservice.courts.documents.CourtDocument;
import br.com.infnet.kafkaservice.courts.repositories.CourtRepository;
import br.com.infnet.kafkaservice.shared.messaging.dtos.CourtEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourtUseCase {

    private final CourtRepository courtRepository;

    public void index(CourtEvent event) {
        CourtDocument doc = new CourtDocument();
        doc.setId(event.courtId().toString());
        doc.setName(event.name());
        doc.setSportType(event.sportType());
        doc.setSurfaceType(event.surfaceType());
        doc.setCity(event.city());
        doc.setState(event.state());
        doc.setStreet(event.street());
        doc.setZipCode(event.zipCode());
        doc.setActive(event.active());
        doc.setOpenTime(event.openTime());
        doc.setCloseTime(event.closeTime());
        courtRepository.save(doc);
    }
}
