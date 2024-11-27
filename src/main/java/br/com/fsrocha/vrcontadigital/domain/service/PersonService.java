package br.com.fsrocha.vrcontadigital.domain.service;

import br.com.fsrocha.vrcontadigital.domain.model.PersonEntity;

import java.util.Optional;

public interface PersonService {

    Optional<PersonEntity> findByCpf(String cpf);

}
