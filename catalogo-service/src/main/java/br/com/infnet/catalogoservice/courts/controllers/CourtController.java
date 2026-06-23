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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtController {

    private static final Logger logger = LoggerFactory.getLogger(CourtController.class);
    private final CreateCourtUseCase createCourtUseCase;
    private final GetCourtByIdUseCase getCourtByIdUseCase;
    private final UpdateCourtUseCase updateCourtUseCase;
    private final GetAllCourtUseCase getAllCourtUseCase;


    @PostMapping
    public ResponseEntity<CourtCreateResponseDTO> create(
            @RequestBody @Valid CourtRequestDTO dto
    ){
        logger.info("Criando quadra: name={}", dto.name());
        CourtCreateResponseDTO response = createCourtUseCase.execute(dto);
        logger.info("Quadra criada: id={}", response.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtResponseDTO> findById(@PathVariable UUID id) {
        logger.info("Buscando quadra: id={}", id);
        CourtResponseDTO response = getCourtByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourtUpdateResponseDTO> update(@PathVariable UUID id, @RequestBody CourtRequestDTO dto) {
        logger.info("Atualizando quadra: id={}", id);
        CourtUpdateResponseDTO response = updateCourtUseCase.execute(id, dto);
        logger.info("Quadra atualizada: id={}", id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<PaginationDTO<CourtResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage
    ) {
        PaginationDTO<CourtResponseDTO> result = getAllCourtUseCase.execute(page, perPage);
        logger.info("Listando quadras: total={}, page={}, perPage={}",  result.total(), page, perPage);
        return ResponseEntity.ok(result);
    }
}
