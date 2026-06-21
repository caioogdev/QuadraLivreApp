package br.com.infnet.kafkaservice.shared.messaging.dtos;

import java.util.UUID;

public record CourtEvent(
        String eventType,
        UUID courtId,
        String name,
        String sportType,
        String surfaceType,
        String city,
        String state,
        String zipCode,
        String street,
        Boolean active,
        String openTime,
        String closeTime
) {}