package br.com.infnet.catalogoservice.courts.usecases;

import br.com.infnet.catalogoservice.courts.dtos.rep.CourtUpdateResponseDTO;
import br.com.infnet.catalogoservice.courts.dtos.req.CourtRequestDTO;
import br.com.infnet.catalogoservice.courts.mappers.CourtMapper;
import br.com.infnet.catalogoservice.courts.models.Court;
import br.com.infnet.catalogoservice.courts.repositories.CourtRepository;
import br.com.infnet.catalogoservice.shared.messaging.dtos.CourtEvent;
import br.com.infnet.catalogoservice.shared.messaging.producers.CourtProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCourtUseCase {

    private final CourtRepository courtRepository;
    private final CourtMapper courtMapper;
    private final CourtProducer courtProducer;

    @Transactional
    public CourtUpdateResponseDTO execute(UUID id, CourtRequestDTO dto) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Court not found with id: " + id));

        if (dto.name() != null) court.setName(dto.name());
        if (dto.description() != null) court.setDescription(dto.description());
        if (dto.sportType() != null) court.setSportType(dto.sportType());
        if (dto.surfaceType() != null) court.setSurfaceType(dto.surfaceType());
        if (dto.covered() != null) court.setCovered(dto.covered());
        if (dto.capacity() != null) court.setCapacity(dto.capacity());
        if (dto.active() != null) court.setActive(dto.active());
        if (dto.address() != null) court.setAddress(courtMapper.toAddress(dto.address()));
        if (dto.openTime() != null) court.setOpenTime(dto.openTime());
        if (dto.closeTime() != null) court.setCloseTime(dto.closeTime());

        Court saved = courtRepository.save(court);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                courtProducer.publishUpdated(CourtEvent.from(saved, "COURT_UPDATED"));
            }
        });

        return new CourtUpdateResponseDTO(saved.getId(), "Court updated successfully");
    }
}