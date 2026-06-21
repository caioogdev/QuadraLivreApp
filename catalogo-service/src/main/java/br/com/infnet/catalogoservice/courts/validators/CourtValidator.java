package br.com.infnet.catalogoservice.courts.validators;

import br.com.infnet.catalogoservice.courts.repositories.CourtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CourtValidator {

    private final CourtRepository courtRepository;

    public boolean existsCourtByName(String name){
        return courtRepository.existsCourtByName(name);
    }

    public boolean existsCourtByNameAndIdNot(String name, UUID id) {
        return courtRepository.existsByNameAndIdNot(name, id);
    }
}
