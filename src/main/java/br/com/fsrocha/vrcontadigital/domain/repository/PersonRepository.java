package br.com.fsrocha.vrcontadigital.domain.repository;

import br.com.fsrocha.vrcontadigital.domain.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {

    Optional<PersonEntity> findByCpf(String cpf);

}
