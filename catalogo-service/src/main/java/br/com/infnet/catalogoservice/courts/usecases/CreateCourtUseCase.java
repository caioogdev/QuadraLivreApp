package br.com.infnet.catalogoservice.courts.usecases;


import br.com.infnet.catalogoservice.courts.dtos.rep.CourtCreateResponseDTO;
import br.com.infnet.catalogoservice.courts.dtos.req.CourtRequestDTO;
import br.com.infnet.catalogoservice.courts.mappers.CourtMapper;
import br.com.infnet.catalogoservice.courts.models.Court;
import br.com.infnet.catalogoservice.courts.repositories.CourtRepository;
import br.com.infnet.catalogoservice.courts.validators.CourtValidator;
import br.com.infnet.catalogoservice.shared.messaging.dtos.CourtEvent;
import br.com.infnet.catalogoservice.shared.messaging.producers.CourtProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class CreateCourtUseCase {
    private final CourtRepository courtRepository;
    private final CourtValidator courtValidator;
    private final CourtMapper courtMapper;
    private final CourtProducer courtProducer;

    @Transactional
    public CourtCreateResponseDTO execute(CourtRequestDTO dto){
        if(courtValidator.existsCourtByName(dto.name())) {
            throw new RuntimeException("Court with this name already exists");
        }

        Court court = courtMapper.toEntity(dto);
        Court saved = courtRepository.save(court);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                courtProducer.publishCreated(CourtEvent.from(saved, "COURT_CREATED"));
            }
        });

        return new CourtCreateResponseDTO(saved.getId(), "Court created successfully");
    }
}
