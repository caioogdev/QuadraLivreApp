package br.com.infnet.catalogoservice.courts.dtos.rep;

import java.util.UUID;

public record CourtUpdateResponseDTO(
        UUID id,
        String message
) {
}
