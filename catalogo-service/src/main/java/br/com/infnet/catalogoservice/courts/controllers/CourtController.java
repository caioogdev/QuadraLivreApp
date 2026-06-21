package br.com.infnet.catalogoservice.courts.controllers;

import br.com.infnet.catalogoservice.courts.dtos.rep.CourtCreateResponseDTO;
import br.com.infnet.catalogoservice.courts.dtos.rep.CourtResponseDTO;
import br.com.infnet.catalogoservice.courts.dtos.rep.CourtUpdateResponseDTO;
import br.com.infnet.catalogoservice.courts.dtos.req.CourtRequestDTO;
import br.com.infnet.catalogoservice.shared.dtos.PaginationDTO;
import br.com.infnet.catalogoservice.courts.usecases.CreateCourtUseCase;
import br.com.infnet.catalogoservice.courts.usecases.GetAllCourtUseCase;
import br.com.infnet.catalogoservice.courts.usecases.GetCourtByIdUseCase;
import br.com.infnet.catalogoservice.courts.usecases.UpdateCourtUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CreateCourtUseCase createCourtUseCase;
    private final GetCourtByIdUseCase getCourtByIdUseCase;
    private final UpdateCourtUseCase updateCourtUseCase;
    private final GetAllCourtUseCase getAllCourtUseCase;


    @PostMapping
    public ResponseEntity<CourtCreateResponseDTO> create(
            @RequestBody @Valid CourtRequestDTO dto
    ){

        CourtCreateResponseDTO response = createCourtUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtResponseDTO> findById(@PathVariable UUID id) {
        CourtResponseDTO response = getCourtByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourtUpdateResponseDTO> update(@PathVariable UUID id, @RequestBody CourtRequestDTO dto) {
        CourtUpdateResponseDTO response = updateCourtUseCase.execute(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<PaginationDTO<CourtResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage
    ) {
        return ResponseEntity.ok(getAllCourtUseCase.execute(page, perPage));
    }
}
