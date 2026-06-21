package br.com.infnet.catalogoservice.courts.dtos.rep;

import br.com.infnet.catalogoservice.courts.dtos.req.AddressRequestDTO;
import br.com.infnet.catalogoservice.courts.enums.SportType;
import br.com.infnet.catalogoservice.courts.enums.SurfaceType;

import java.time.LocalTime;
import java.util.UUID;

public record CourtResponseDTO (
        UUID id,
        String name,
        String description,
        SportType sportType,
        AddressRequestDTO address,
        SurfaceType surfaceType,
        Boolean covered,
        Integer capacity,
        LocalTime openTime,
        LocalTime closeTime,
        Boolean active
){ }
