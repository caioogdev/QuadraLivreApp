package br.com.infnet.catalogoservice.courts.mappers;

import br.com.infnet.catalogoservice.courts.dtos.rep.CourtResponseDTO;
import br.com.infnet.catalogoservice.courts.dtos.req.AddressRequestDTO;
import br.com.infnet.catalogoservice.courts.dtos.req.CourtRequestDTO;
import br.com.infnet.catalogoservice.courts.models.Address;
import br.com.infnet.catalogoservice.courts.models.Court;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourtMapper {

    Court toEntity(CourtRequestDTO dto);

    Address toAddress(AddressRequestDTO dto);

    CourtResponseDTO toResponse(Court dto);
}
