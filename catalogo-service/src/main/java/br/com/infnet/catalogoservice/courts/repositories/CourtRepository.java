package br.com.infnet.catalogoservice.courts.repositories;

import br.com.infnet.catalogoservice.courts.models.Court;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourtRepository extends JpaRepository<Court, UUID> {
    boolean existsCourtByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);

}
