package br.com.infnet.catalogoservice.courts.usecases;


import br.com.infnet.catalogoservice.courts.dtos.rep.CourtResponseDTO;
import br.com.infnet.catalogoservice.courts.mappers.CourtMapper;
import br.com.infnet.catalogoservice.courts.models.Court;
import br.com.infnet.catalogoservice.courts.repositories.CourtRepository;
import br.com.infnet.catalogoservice.shared.dtos.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllCourtUseCase {

    private final CourtRepository courtRepository;
    private final CourtMapper courtMapper;

    public PaginationDTO<CourtResponseDTO> execute(int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Court> result = courtRepository.findAll(pageable);

        List<CourtResponseDTO> data = result.getContent()
                .stream()
                .map(courtMapper::toResponse)
                .toList();

        return new PaginationDTO<>(
                result.getTotalElements(),
                data,
                page,
                perPage,
                result.hasNext(),
                result.hasPrevious()
        );
    }
}
