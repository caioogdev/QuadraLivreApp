package br.com.infnet.catalogoservice.courts.usecases;

import br.com.infnet.catalogoservice.courts.dtos.rep.CourtResponseDTO;
import br.com.infnet.catalogoservice.courts.mappers.CourtMapper;
import br.com.infnet.catalogoservice.courts.models.Court;
import br.com.infnet.catalogoservice.courts.repositories.CourtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCourtByIdUseCase {

    private final CourtRepository courtRepository;
    private final CourtMapper courtMapper;

    public CourtResponseDTO execute(UUID id){
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Court not found with id: " + id));

        return courtMapper.toResponse(court);
    }
}
