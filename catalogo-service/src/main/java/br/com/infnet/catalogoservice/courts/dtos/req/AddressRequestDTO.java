package br.com.infnet.catalogoservice.courts.dtos.req;

public record AddressRequestDTO(
        String street,
        String city,
        String state,
        String zipCode
) {}
