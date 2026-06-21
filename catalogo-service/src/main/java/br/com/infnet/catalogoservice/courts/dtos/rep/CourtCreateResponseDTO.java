package br.com.infnet.catalogoservice.courts.dtos.rep;

import java.util.UUID;

public record CourtCreateResponseDTO(
        UUID id,
        String message
) { }
