package br.com.infnet.catalogoservice.shared.messaging.dtos;

import br.com.infnet.catalogoservice.courts.enums.SportType;
import br.com.infnet.catalogoservice.courts.enums.SurfaceType;
import br.com.infnet.catalogoservice.courts.models.Court;

import java.util.UUID;

public record CourtEvent(
        String eventType,
        UUID courtId,
        String name,
        SportType sportType,
        SurfaceType surfaceType,
        String city,
        String state,
        String zipCode,
        String street,
        Boolean active,
        String openTime,
        String closeTime,
        String createdAt,
        String updatedAt
) {
    public static CourtEvent from(Court court, String eventType) {
        return new CourtEvent(
                eventType,
                court.getId(),
                court.getName(),
                court.getSportType(),
                court.getSurfaceType(),
                court.getAddress().getCity(),
                court.getAddress().getState(),
                court.getAddress().getZipCode(),
                court.getAddress().getStreet(),
                court.getActive(),
                court.getOpenTime().toString(),
                court.getCloseTime().toString(),
                court.getCreatedAt().toString(),
                court.getUpdatedAt().toString()
        );
    }
}
