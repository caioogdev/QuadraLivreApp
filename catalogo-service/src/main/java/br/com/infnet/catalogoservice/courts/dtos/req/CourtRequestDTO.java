package br.com.infnet.catalogoservice.courts.dtos.req;

import br.com.infnet.catalogoservice.courts.enums.SportType;
import br.com.infnet.catalogoservice.courts.enums.SurfaceType;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

public record CourtRequestDTO (
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
